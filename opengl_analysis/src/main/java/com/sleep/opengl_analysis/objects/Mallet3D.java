package com.sleep.opengl_analysis.objects;

import com.sleep.opengl_analysis.airhockey.VertexArray;
import com.sleep.opengl_analysis.programs.ColorShaderProgram_3D;
import com.sleep.opengl_analysis.util.Geometry;

import java.util.List;

/**
 * author：xingkong on 2020-02-28
 * e-mail：xingkong@changjinglu.net
 */
public class Mallet3D {
    private static final int POSITION_COMPONENT_COUNT = 3;
    public final float radius, height;
    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Mallet3D(float radius, float height, int numPointAround) {
        ObjectBuilder.GenerateData generateData = ObjectBuilder.createPuck(
                new Geometry.Cylinder(new Geometry.Point(0f, 0f, 0f), radius, height), numPointAround);
        this.radius = radius;
        this.height = height;
        vertexArray = new VertexArray(generateData.vertexData);
        drawList = generateData.drawList;
    }

    public void bindData(ColorShaderProgram_3D colorProgram) {
        vertexArray.setVertexAttribPointer(0, colorProgram.getPositionLocation(), POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (ObjectBuilder.DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
