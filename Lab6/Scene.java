package Lab6;

public class Scene {
    public Light[] lightSources;
    public Sphere sphere;
    public Vector3D ambientLight;
    public int imageHeight, imageWidth;

    public Scene(Light[] lightSources, Sphere sphere, Vector3D ambientLight, int imageHeight, int imageWidth) {
        this.lightSources = lightSources;
        this.sphere = sphere;
        this.ambientLight = ambientLight;
        this.imageHeight = imageHeight;
        this.imageWidth = imageWidth;
    }
}
