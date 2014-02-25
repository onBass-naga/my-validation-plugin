import com.example.validation.DateConstraint
import org.codehaus.groovy.grails.validation.ConstrainedProperty

class MyValidationPluginGrailsPlugin {
    // the plugin version
    def version = "0.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "2.3 > *"
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def groupId = 'com.example'
    def title = "My Validation Plugin" // Headline display name of the plugin
    def author = "naga"
    def authorEmail = ""
    def description = '''\
日付文字列のチェックを行うためのプラグイン（サンプル）
'''

    // URL to the plugin's documentation
    def documentation = '' //"http://grails.org/plugin/my-validation-plugin"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
    def license = "APACHE"

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.codehaus.org/grails-plugins/" ]

    def loadAfter = ['controllers']

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
        ConstrainedProperty.registerNewConstraint(
                DateConstraint.DATE_CONSTRAINT, DateConstraint.class);
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { ctx ->
    }

    def onChange = { event ->
    }

    def onConfigChange = { event ->
    }

    def onShutdown = { event ->
    }
}
