# StarExplorer
An Android application using OpenGL ES to display a sphere of stars.  The star locations are derived from a map obtained from the Vizier website.

The application starts with MainActivity.

MainActivity owns the buttons and the MainView.

MainView (GLSurfaceView) owns the MainRenderer.

MainRenderer owns the SceneManager; it also dictates which ModelObjects and CameraObjects get created and adds them to the SceneManager.

ModelObject instances:
PointLight: The red pixel at the center of the display.
CelestialSphere: The lat/lon lines which describe the sphere.
StarField: The population of stars which are painted on the (virtual) surface of the sphere.

CameraObject instances:
Camera4:  The main camera in the application.
