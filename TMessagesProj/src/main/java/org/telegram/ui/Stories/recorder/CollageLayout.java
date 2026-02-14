package org.telegram.ui.Stories.recorder;

/**
 * CollageLayout stub - Stories removed in Tony Chat
 */
public class CollageLayout {
    public static class Part {
        public float x, y, width, height;
        public float l(float scale) { return x * scale; }
        public float r(float scale) { return (x + width) * scale; }
        public float t(float scale) { return y * scale; }
        public float b(float scale) { return (y + height) * scale; }
        public float w(float scale) { return width * scale; }
        public float h(float scale) { return height * scale; }
    }
}
