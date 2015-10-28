package hello

import com.intellij.openapi.util.Disposer
import com.intellij.psi.search.GlobalSearchScope
import org.jetbrains.kotlin.cli.common.CLIConfigurationKeys
import org.jetbrains.kotlin.cli.common.messages.*
import org.jetbrains.kotlin.cli.jvm.compiler.*
import org.jetbrains.kotlin.cli.jvm.config.JVMConfigurationKeys
import org.jetbrains.kotlin.cli.jvm.config.addJvmClasspathRoots
import org.jetbrains.kotlin.cli.jvm.config.getModuleName
import org.jetbrains.kotlin.config.CompilerConfiguration
import org.jetbrains.kotlin.config.addKotlinSourceRoot
import org.jetbrains.kotlin.descriptors.PackageFragmentProvider
import org.jetbrains.kotlin.frontend.java.di.createContainerForTopDownAnalyzerForJvm
import org.jetbrains.kotlin.incremental.components.LookupTracker
import org.jetbrains.kotlin.load.java.JvmAbi
import org.jetbrains.kotlin.psi.KtBlockExpression
import org.jetbrains.kotlin.resolve.AnalyzerScriptParameter
import org.jetbrains.kotlin.resolve.TopDownAnalysisContext
import org.jetbrains.kotlin.resolve.TopDownAnalysisMode
import org.jetbrains.kotlin.resolve.jvm.JvmAnalyzerFacade
import org.jetbrains.kotlin.resolve.jvm.TopDownAnalyzerFacadeForJVM
import org.jetbrains.kotlin.resolve.lazy.declarations.FileBasedDeclarationProviderFactory
import org.jetbrains.kotlin.utils.PathUtil
import java.io.File
import java.util.*
import java.util.logging.Logger

class KotlinScriptParser {
    companion object {
        private val LOG = Logger.getLogger(KotlinScriptParser.javaClass.name)
        private val messageCollector = object : MessageCollector {
            override fun report(severity: CompilerMessageSeverity, message: String, location: CompilerMessageLocation) {
                val path = location.path
                val position = if (path == null) "" else "$path: (${location.line}, ${location.column}) "

                val text = position + message

                if (CompilerMessageSeverity.VERBOSE.contains(severity)) {
                    LOG.finest(text)
                } else if (CompilerMessageSeverity.ERRORS.contains(severity)) {
                    LOG.severe(text)
                } else if (severity == CompilerMessageSeverity.INFO) {
                    LOG.info(text)
                } else {
                    LOG.warning(text)
                }
            }
        }

        private val classPath: ArrayList<File> by lazy {
            val classpath = arrayListOf<File>()
            classpath += PathUtil.getResourcePathForClass(AnnotationTarget.CLASS.javaClass)
            classpath
        }
    }

    fun parse(vararg files: String): TopDownAnalysisContext {
        // The Kotlin compiler configuration
        val configuration = CompilerConfiguration()

        val groupingCollector = GroupingMessageCollector(messageCollector)
        val severityCollector = MessageSeverityCollector(groupingCollector)
        configuration.put(CLIConfigurationKeys.MESSAGE_COLLECTOR_KEY, severityCollector)


        configuration.addJvmClasspathRoots(PathUtil.getJdkClassesRoots())
        // The path to .kt files sources
        files.forEach { configuration.addKotlinSourceRoot(it) }
        // Configuring Kotlin class path
        configuration.addJvmClasspathRoots(classPath)
        configuration.put(JVMConfigurationKeys.MODULE_NAME, JvmAbi.DEFAULT_MODULE_NAME)
        configuration.put<List<AnalyzerScriptParameter>>(JVMConfigurationKeys.SCRIPT_PARAMETERS, CommandLineScriptUtils.scriptParameters())

        val rootDisposable = Disposer.newDisposable()
        try {
            val environment = KotlinCoreEnvironment.createForProduction(rootDisposable, configuration, EnvironmentConfigFiles.JVM_CONFIG_FILES)
            val ktFiles = environment.getSourceFiles()
            val sharedTrace = CliLightClassGenerationSupport.NoScopeRecordCliBindingTrace()
            val moduleContext = TopDownAnalyzerFacadeForJVM.createContextWithSealedModule(environment.project,
                    environment.getModuleName())

            val project = moduleContext.project
            val allFiles = JvmAnalyzerFacade.getAllFilesToAnalyze(project, null, ktFiles)
            val providerFactory = FileBasedDeclarationProviderFactory(moduleContext.storageManager, allFiles)
            val lookupTracker = LookupTracker.DO_NOTHING
            val packagePartProvider = JvmPackagePartProvider(environment)
            val container = createContainerForTopDownAnalyzerForJvm(
                    moduleContext,
                    sharedTrace,
                    providerFactory,
                    GlobalSearchScope.allScope(project),
                    lookupTracker,
                    packagePartProvider)

            val additionalProviders = ArrayList<PackageFragmentProvider>()

            additionalProviders.add(container.javaDescriptorResolver.packageFragmentProvider)

            return container.lazyTopDownAnalyzerForTopLevel.analyzeFiles(TopDownAnalysisMode.LocalDeclarations, allFiles, additionalProviders)
        } finally {
            rootDisposable.dispose()
            if (severityCollector.anyReported(CompilerMessageSeverity.ERROR)) {
                throw RuntimeException("Compilation error")
            }
        }
    }
}


fun main(args: Array<String>) {
    val scriptFile = "/media/data/java/blackfern/kotlin-compile-test/test.kt"

    val parser = KotlinScriptParser()

    val analyzeContext = parser.parse(scriptFile)

    val function = analyzeContext.functions.keys.first()
    val body = function.bodyExpression as KtBlockExpression
    val i = 0;
}
