//顶点着色器中也可以设置精度，因为精度对于顶点着色器太重要了，所以默认是高精度。

attribute vec4 a_Position;
uniform mat4 u_Matrix;

void main(){
    gl_Position = u_Matrix * a_Position;
    gl_PointSize = 10.0;
}