//顶点着色器中也可以设置精度，因为精度对于顶点着色器太重要了，所以默认是高精度。

attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec4 v_Color;

void main(){
    v_Color = a_Color;
    gl_Position = a_Position;
    gl_PointSize = 10.0;
}