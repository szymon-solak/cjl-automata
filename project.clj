(defproject cellular-automata "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.lwjgl/lwjgl "3.2.3"]
                 [org.lwjgl/lwjgl "3.2.3" :classifier "natives-linux"]
                 [org.lwjgl/lwjgl-glfw "3.2.3"]
                 [org.lwjgl/lwjgl-glfw "3.2.3" :classifier "natives-linux"]
                 [org.lwjgl/lwjgl-opengl "3.2.3"]
                 [org.lwjgl/lwjgl-opengl "3.2.3" :classifier "natives-linux"]
                 [org.jetbrains.skija/skija-linux "0.93.1"]]
  :repositories {"space-maven" "https://packages.jetbrains.team/maven/p/skija/maven"}
  :main ^:skip-aot cellular-automata.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
