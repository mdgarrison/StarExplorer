uniform mat4 u_Matrix;

attribute vec4 a_Position;
attribute vec4 a_Color;

varying vec4 v_Color;

void main()
{
    v_Color = a_Color;
    gl_Position = u_Matrix * a_Position;
    
    if ((a_Color.r > 0.95) || (a_Color.g > 0.95)) {
        gl_PointSize = 4.0;
    }
    else if (a_Color.r > 0.14) {
        gl_PointSize = 2.0;
    }
    else {
        gl_PointSize = 1.0;
    }
}
