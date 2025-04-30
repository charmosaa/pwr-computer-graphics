package Lab6;

public class Material {
    
    Vector3D kd, ks, ka, sl;
    double g;

    public Material(Vector3D kd, Vector3D ks, Vector3D ka, Vector3D sl, double g) {
        this.kd = kd;   // diffuse reflection coefficients: kdR, kdG, kdB
        this.ks = ks;   // specular reflection coefficients: ksR, ksG, ksB
        this.ka = ka;   // ambient light diffuse reflection coefficients: kaR, kaG, kaB
        this.sl = sl;   // self luminance RGB
        this.g = g;     // shiness
    }
    
}
