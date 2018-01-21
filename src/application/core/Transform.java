package application.core;

import application.math.Mat44;
import application.math.Vec2;
import application.math.Vec3;

public class Transform {
    private Mat44 transform;

    private Mat44 scale, rotation, translate;

    public Transform() {
        scale = new Mat44(1);
        rotation = new Mat44(1);
        translate = new Mat44(0);
        update();
    }

    public Transform(Vec3 translate, float rotation, Vec2 scale) {
        this.scale = initScale(scale);
        this.rotation = initRotation(rotation);
        this.translate = initTranslate(translate);
        update();
    }

    private void update() {
        transform = scale.mul(rotation).mul(translate);
    }

    private static Mat44 initTranslate(Vec3 translate) {
        return new Mat44(new float[]{
                1, 0, 0, translate.x,
                0, 1, 0, translate.y,
                0, 0, 1, translate.z,
                0, 0, 0, 1
        });
    }

    private static Mat44 initRotation(float angle) {
        float cs = (float) (Math.cos(Math.toRadians(angle)));
        float sn = (float) (Math.sin(Math.toRadians(angle)));
        return new Mat44(new float[]{
                cs, -sn, 0, 1,
                sn, cs, 0, 1,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }

    private static Mat44 initScale(Vec2 scale) {
        return new Mat44(new float[]{
                scale.x, 0, 0, 1,
                0, scale.y, 0, 1,
                0, 0, 1, 0,
                0, 0, 0, 1
        });
    }
}
