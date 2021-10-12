(ns cellular-automata.renderers
  (:require
   [clojure.string :as string])
  (:import
   [org.jetbrains.skija BackendRenderTarget ColorSpace DirectContext FramebufferFormat Paint Rect Surface SurfaceColorFormat SurfaceOrigin]
   [org.lwjgl.glfw Callbacks GLFW GLFWErrorCallback]
   [org.lwjgl.opengl GL GL11]
   [org.lwjgl BufferUtils]
   [org.lwjgl.system MemoryUtil]))

(defn print-grid [grid] (println (string/join \newline grid)))

(defn console [grid]
  (println "\n\n")
  (print-grid grid))

(defn display-scale [window]
  (let [x (make-array Float/TYPE 1)
        y (make-array Float/TYPE 1)]
    (GLFW/glfwGetWindowContentScale window x y)
    [(first x) (first y)]))

(defn color [^long l]
  (.intValue (Long/valueOf l)))

(defn- glfw-window-size [window]
  (let [width-buffer (BufferUtils/createIntBuffer 1)
        height-buffer (BufferUtils/createIntBuffer 1)]
    (GLFW/glfwGetWindowSize (long window) width-buffer height-buffer)
    (let [window-width (.get width-buffer)
          window-height (.get height-buffer)]
      [window-width window-height])))

(defn gui-create-window [width height]
  (.set (GLFWErrorCallback/createPrint System/err))
  (GLFW/glfwInit)
  (GLFW/glfwWindowHint GLFW/GLFW_VISIBLE GLFW/GLFW_FALSE)
  (GLFW/glfwWindowHint GLFW/GLFW_RESIZABLE GLFW/GLFW_TRUE)
  (let [window (GLFW/glfwCreateWindow width height "Cellular Automata" MemoryUtil/NULL MemoryUtil/NULL)]
    (GLFW/glfwMakeContextCurrent window)
    (GLFW/glfwSwapInterval 1)
    (GLFW/glfwShowWindow window)
    (GL/createCapabilities)
    window))

(defn gui-draw [window grid]
  (let [context (DirectContext/makeGL)
        [width height] (glfw-window-size window)
        cell-height (int (/ height (count grid)))
        cell-width (int (/ width (count (first grid))))
        fb-id   (GL11/glGetInteger 0x8CA6)
        [scale-x scale-y] (display-scale window)
        target  (BackendRenderTarget/makeGL (* scale-x width) (* scale-y height) 0 8 fb-id FramebufferFormat/GR_GL_RGBA8)
        surface (Surface/makeFromBackendRenderTarget context target SurfaceOrigin/BOTTOM_LEFT SurfaceColorFormat/RGBA_8888 (ColorSpace/getSRGB))
        canvas  (.getCanvas surface)]

    (.scale canvas scale-x scale-y)
    (when (not (GLFW/glfwWindowShouldClose window))
      (let [layer (.save canvas)
            alive-color (doto (Paint.) (.setColor (color 0xFF000000)))
            dead-color (doto (Paint.) (.setColor (color 0xFFFFFFFF)))]
        (.clear canvas (color 0xFFFFFFFF))

        (doseq [row (range 0 (count grid))]
          (doseq [col (range 0 (count (first grid)))]
            (let [cell (get-in grid [row col])
                  color (if (= cell 1) alive-color dead-color)]
              (.drawRect
               canvas
               (Rect/makeXYWH (* cell-width col) (* cell-height row) cell-width cell-height)
               color))))

        (.restoreToCount canvas layer))
      (.flush context)
      (GLFW/glfwSwapBuffers window)
      (GLFW/glfwPollEvents))))

(defn gui-destroy-window [window]
  ((Callbacks/glfwFreeCallbacks window)
   (GLFW/glfwHideWindow window)
   (GLFW/glfwDestroyWindow window)
   (GLFW/glfwPollEvents)
   (GLFW/glfwTerminate)
   (.free (GLFW/glfwSetErrorCallback nil))
   (shutdown-agents)))
