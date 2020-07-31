package io.gles;

import io.gles.run.ClientRun;
import io.gles.run.SearchRun;
import io.gles.run.ServerRun;

import static io.gles.run.SystemUtil.getProperty;

public class Main {

    public static void main(String[] args) {

        try {
            String mode = getProperty("mode", "server");

            switch (mode) {

                case "client":
                    ClientRun.main(args);
                    break;

                case "search":
                    SearchRun.main(args);
                    break;

                case "server":
                default:
                    ServerRun.main(args);
                    break;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
