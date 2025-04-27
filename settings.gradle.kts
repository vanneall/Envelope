pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Envelope"
include(":app")
include(":core")
include(":core:network")
include(":core:ui")
include(":feature")
include(":feature:auth")
include(":feature:auth")
include(":feature:chats")
include(":core:user")
include(":feature:contacts")
include(":core:utils")
include(":feature:settings")
include(":core:navigation")
include(":core:kotlin-utils")
include(":core:services")
include(":core:services:user")
include(":core:services:auth")
include(":core:services:chats")
include(":core:services:media")
include(":core:settings")
