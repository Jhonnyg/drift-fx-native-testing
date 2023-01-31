JAVA_HOME = os.getenv("JAVA_HOME")

solution "solution"
    language       ( "C++" )
    location       ( "build" )

    configurations { "Debug", "Release" }
    platforms      { "x64" }
    flags          { "FatalWarnings", "NoPCH", "Cpp11" }

    configuration "Debug"
        defines { "DEBUG" }
        flags   { "Symbols" }

    configuration "Release"
        defines { "NDEBUG" }
        flags   { "Optimize" }

project "native-dynamic"
    objdir     ( "build" )
    kind       ( "SharedLib" )
    targetname ( "native-dynamic" )
    targetdir  ( "build" )
    files      {
        "src/native.cpp",
        "src/driftcpp.cpp",
    }
    links      { "OpenGL.framework" }

    includedirs {
        path.join(JAVA_HOME, "include"),
        path.join(JAVA_HOME, "include", "darwin"),
        "src"
    }
