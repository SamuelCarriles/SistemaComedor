import clases.*;
import swingUI.Login;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        Sistema.iniciar();
        SwingUtilities.invokeLater(() -> new Login());

    }
}