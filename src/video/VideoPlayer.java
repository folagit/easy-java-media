/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package video;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.stage.FileChooser;

/**
 *
 * @author Mandar
 */
public class VideoPlayer extends javafx.application.Application {

    private static String arg1;
    private static FileChooser fc;    

    @Override
    public void start(final javafx.stage.Stage stage) throws IOException {

        fc = new FileChooser();
        stage.setTitle("Media Player");
        javafx.scene.Group root = new javafx.scene.Group();
        Scene scene = new Scene(root, 600, 265);

        // getting file path
        String s = fc.showOpenDialog(stage).getCanonicalPath();
        String r = s.replace('\\', '/');
        r = "file:///".concat(r);

        // create media player
        javafx.scene.media.Media media = new javafx.scene.media.Media((arg1 != null) ? arg1 : r);
        javafx.scene.media.MediaPlayer mediaPlayer = new javafx.scene.media.MediaPlayer(media);
        VideoControl mediaControl = new VideoControl(mediaPlayer, stage);

        mediaPlayer.setAutoPlay(true);

        scene.setRoot(mediaControl);
        scene.getStylesheets().add(VideoPlayer.class.getResource("videoplayer.css").toExternalForm());
        
        ToggleButton fullScreen = new ToggleButton();
        fullScreen.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                if (((ToggleButton) t.getSource()).isSelected()) {
                    stage.setFullScreen(true);
                } else {
                    stage.setFullScreen(false);
                }
                    
            }
        });
        
        // show stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        if (args.length > 0) {
            arg1 = args[0];
        }
        javafx.application.Application.launch(args);
    }
}