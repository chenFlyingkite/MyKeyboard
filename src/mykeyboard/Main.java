package mykeyboard;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import util.logging.L;
import util.tool.TicTac;

import java.util.Locale;

public class Main {
    // https://blogs.longwin.com.tw/lifetype/key_codes.html
    // https://stackoverflow.com/questions/16179923/how-to-capture-global-key-presses-in-java
    // Add jnativehook to Project
    // File -> Project Structure
    // -> Project Settings . Libraries . + . From maven
    // -> com.1stleg:jnativehook:2.1.0
    // -> https://github.com/kwhat/jnativehook

    public static void main(String[] args) {
        L.log("Hello world");
        TicTac.tic();
        s();
        TicTac.tac("Ended run");
        L.log("Ended");
    }

    private static void s() {
        // https://blogs.longwin.com.tw/lifetype/key_codes.html
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(new NativeKeyListener() {
                @Override
                public void nativeKeyTyped(NativeKeyEvent event) {
                    L.log("Eric typed %s", text(event));
                }

                @Override
                public void nativeKeyPressed(NativeKeyEvent event) {
                    L.log("Eric pressed %s", text(event));
                    // public NativeKeyEvent(int id, int modifiers, int rawCode, int keyCode, char keyChar) {
                    if (event.getKeyCode() == NativeKeyEvent.VC_F4) {
                        NativeKeyEvent m = new NativeKeyEvent(
                                NativeKeyEvent.NATIVE_KEY_PRESSED,
                                //NativeKeyEvent.NATIVE_KEY_PRESSED,
                                0,
                                112,
                                NativeKeyEvent.VC_F1,
                                NativeKeyEvent.CHAR_UNDEFINED);
                        NativeKeyEvent n = new NativeKeyEvent(
                                NativeKeyEvent.NATIVE_KEY_RELEASED,
                                //NativeKeyEvent.NATIVE_KEY_PRESSED,
                                0,
                                112,
                                NativeKeyEvent.VC_F1,
                                NativeKeyEvent.CHAR_UNDEFINED);
                        GlobalScreen.postNativeEvent(m);
                        GlobalScreen.postNativeEvent(n);
                    }
                    //GlobalScreen.postNativeEvent(n);
                }

                @Override
                public void nativeKeyReleased(NativeKeyEvent event) {
                    L.log("Eric released %s", text(event));
                }

                private String text(NativeKeyEvent e) {
                    String s = NativeKeyEvent.getKeyText(e.getKeyCode());
                    return String.format(Locale.US,
                            "\n---\nkeyText = %s\nid = %s\nmodifiers = %s\nrawCode = %s\nkeyCode = %s\nkeyChar = %s = %d : %s\nkeyLocation = %s\n---\n",
                            s, e.getID(), e.getModifiers(), e.getRawCode(), e.getKeyCode(), e.getKeyChar(), (int) e.getKeyChar(), e.getKeyChar() == NativeKeyEvent.CHAR_UNDEFINED, e.getKeyLocation()
                    );

                    //return NativeKeyEvent.getKeyText(event.getKeyCode());
                }
            });
        } catch (NativeHookException e) {
            e.printStackTrace();
        }

    }
}
