//定义所有浮点数据的默认精度 可以选择lowp、mediump、highp分别对应低精度，中等精度，高精度。
//现实中只有部分硬件支持高精度
precision mediump float;

varying vec4 v_Color;

void main(){
    gl_FragColor = v_Color;
}