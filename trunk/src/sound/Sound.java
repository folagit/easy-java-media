/**
 * Description of this project
 */
package sound;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.sound.sampled.*;

/**
 * Contains useful methods for sound capture and playback.
 *
 * These methods use the new {@code javax.sound.sampled} API. All these methods
 * can (currently) only play files with the {@code .wav}, {@code .aiff}, or the
 * {@code .au} file formats.
 *
 * @author Shardul
 */
public class Sound {
    
    /**
     * Loads and plays a specified {@code .wav}, {@code .au}, or {@code .aiff}
     * audio file in the background.
     *
     * This method first loads the complete file into memory, and then plays it
     * in a separate {@code Thread}. This method is not recommended for long
     * files and is not guaranteed to work with files which do not follow the {@code .wav}, {@code .aiff},
     * or {@code .au} file formats. For long files, refer to the 'See Also'
     * section.
     *
     * @param filePath A {@code String} specifying the audio file to be played.
     *
     * @see #bufferedPlay(java.lang.String)
     * @see #preloadedLoop(java.lang.String)
     * @see #preloadedLoop(java.lang.String, int)
     */
    public static void preloadedPlay(final String filePath) {
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                Path path = Paths.get(filePath);
                File file = path.toFile();

                //if path is correct, file is audio, etc... (see the 'isFileSupported(file)' 
                //method)
                if (isFileSupported(file)) {
                    try {
                        //get a 'Clip' (a Clip is a type of output, where you first load 
                        //the entire file and then play it) 
                        Clip clip = AudioSystem.getClip();

                        //feed the file into the clip
                        clip.open(AudioSystem.getAudioInputStream(file));

                        //add a 'listener' to 'listen' for the end of the file
                        clip.addLineListener(new LineListener() {

                            public void update(LineEvent event) {
                                if (event.getType() == LineEvent.Type.STOP) {
                                    event.getLine().close();
                                }
                            }
                        });

                        //start playing sound
                        clip.start();

                        //wait for sound to end, and then return
                        Thread.sleep((long) getDurationWav(file));

                        //catch exceptions
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException ex) {
                        System.err.printf("An exception occurred! %d", ex.toString());
                    } //end try-catch
                } //end if
            }
        });
        t.start();
    }

    /**
     * Checks if the requested file exists and is supported by the music
     * players.
     *
     * This method checks for the existence of a given path, the path's actually
     * leading to a file (not a directory), and for the format of the file to
     * check whether it is audio, and is in a supported file format.
     *
     * @param file A {@code File} object representing the file to be checked
     *
     * @return True if all conditions are met; false otherwise
     */
    static boolean isFileSupported(File file) {
        try {
            AudioFileFormat form = AudioSystem.getAudioFileFormat(file);
            if ((form.getType() == AudioFileFormat.Type.WAVE) || (form.getType() == AudioFileFormat.Type.AU) || (form.getType() == AudioFileFormat.Type.AIFF)) {
                return true;
            } else {
                return false;
            }
        } catch (UnsupportedAudioFileException | IOException ex) {
            System.err.printf("An exception occurred! %d", ex.toString());
            return false;
        }
    }

    /**
     * Loops a {@code .wav}, {@code .au}, or {@code .aiff} audio file for the
     * specified number of times by repeatedly
     * loading and playing it.
     *
     * This method makes use of {@code preloadedPlay(java.lang.String)}. This
     * method is not recommended for long files. This method is not guaranteed
     * to work with files which do not follow the {@code .wav}, {@code .aiff},
     * or {@code .au} file formats.
     *
     * @param filePath A {@code String} specifying the audio file to be played.
     * @param points   The number of times to loop the file. If 0, loops
     * continuously.
     *
     * @see #preloadedPlay(java.lang.String)
     * @see #bufferedLoop(java.lang.String)
     * @see #bufferedLoop(java.lang.String, int)
     * @see #preloadedLoop(java.lang.String)
     */
    public static void preloadedLoop(final String filePath, final int points) {

        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                //get 'File' object representation of String file path
                Path path = Paths.get(filePath);
                File file = path.toFile();

                //if path is correct, file is audio, etc... (see the 'isFileSupported(file)' 
                //method)
                if (isFileSupported(file)) {
                    try {

                        //get a 'Clip' (a Clip is a type of output, where you first load 
                        //the entire file and then play it) 
                        Clip clip = AudioSystem.getClip();

                        //feed the file into the clip
                        clip.open(AudioSystem.getAudioInputStream(file));

                        //add a 'listener' to 'listen' for the end of the file
                        clip.addLineListener(new LineListener() {

                            public void update(LineEvent event) {
                                if (event.getType() == LineEvent.Type.STOP) {
                                    event.getLine().close();
                                }
                            }
                        });

                        //loop for specified number of times; if 0, loop forever
                        if (points > 0) {
                            clip.loop(points);
                            Thread.sleep((long) getDurationWav(file) * points);
                        } else {
                            clip.loop(Clip.LOOP_CONTINUOUSLY);

                            //wait forever
                            while (true) {
                                Thread.sleep(Long.MAX_VALUE);
                            }
                        } //end if-else

                        //catch exceptions    
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException | InterruptedException ex) {
                        System.err.printf("An exception occurred! %d", ex.toString());
                    } //end try-catch
                } //end if
            }
        });

        t.start();
    }

    /**
     * Loops a {@code .wav}, {@code .au}, or {@code .aiff} audio file forever by
     * repeatedly loading and playing it.
     *
     * @param filePath A {@code String} specifying the audio file to be played.
     *
     * @see #preloadedPlay(java.lang.String)
     * @see #preloadedLoop(java.lang.String, int)
     * @see #bufferedLoop(java.lang.String)
     * @see #bufferedLoop(java.lang.String, int)
     */
    public static void preloadedLoop(String filePath) {
        preloadedLoop(filePath, 0);
    }

    /**
     * Gets the duration of a {@code .wav}, {@code .au}, or {@code .aiff} audio
     * file in seconds.
     *
     * This method is not guaranteed to work with files which do not follow the
     * {@code .wav}, {@code .aiff}, or {@code .au} file formats.
     *
     * @param file A {@code File} object specifying the audio file whose
     * duration is sought.
     *
     * @return The file's duration, in seconds.
     */
    static double getDurationWav(File file) {

        //first get a stream of audio representing audio file
        try (AudioInputStream stream = AudioSystem.getAudioInputStream(file)) {

            //then, based on its technical specifications, calculate length
            //this is the part where only .wav, .aiff, or. au files are supported
            AudioFormat format = stream.getFormat();
            return (file.length() / format.getSampleRate() / (format.getSampleSizeInBits() / 8.0) / format.getChannels()) * 1000;

            //catch exceptions
        } catch (UnsupportedAudioFileException | IOException ex) {
            System.err.printf("An exception occurred! %d", ex.toString());
            return -1;
        }
    }

    /**
     * Buffers and plays a specified {@code .wav}, {@code .au}, or {@code .aiff}
     * audio file.
     *
     * This method continuously streams and, at the same time, plays audio from
     * the specified file until the audio is over. This method is useful when
     * the duration of the file is unknown, the audio is being streamed live, or
     * the file is long. This method is not guaranteed to work with files which
     * do not follow the {@code .wav}, {@code .aiff}, or {@code .au} file
     * formats.
     *
     * @param filePath A {@code String} specifying the audio file to be played.
     *
     * @see #preloadedPlay(java.lang.String)
     * @see #bufferedLoop(java.lang.String)
     * @see #bufferedLoop(java.lang.String, int)
     */
    public static void bufferedPlay(final String filePath) {

        //get 'File' object representation of String file path
        Path path = Paths.get(filePath);
        File file = path.toFile();

        //if path is correct, file is audio, etc... (see the 'isFileSupported(file)' 
        //method)
        if (isFileSupported(file)) {
            try {

                //get a stream of data which can load audio little by little
                AudioInputStream ais = AudioSystem.getAudioInputStream(file);

                //get info about an output which should have the capabilty to load
                //data little by little
                DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, ais.getFormat());

                try (SourceDataLine srcLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo)) {
                    final int EXTERNAL_BUFFER_SIZE = 524288;
                    final byte[] ABDATA = new byte[EXTERNAL_BUFFER_SIZE];
                    int nBytesRead = 0;

                    //feed the file into the output
                    srcLine.open(ais.getFormat());

                    //prepare for start
                    srcLine.start();

                    //continuously write a EXTERNAL_BUFFER_SIZE number of bytes to 
                    //the output until the file is over 
                    while (nBytesRead != -1) {
                        nBytesRead = ais.read(ABDATA, 0, ABDATA.length);
                        if (nBytesRead >= 0) {
                            srcLine.write(ABDATA, 0, nBytesRead);
                        } //end if
                    } //end while

                    //drain the buffer of any remaining audio (this also ensures 
                    //the 'remaining' audio is played) and stop playback.
                    srcLine.drain();
                    srcLine.stop();
                }

                //catch exceptions
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                System.err.printf("An exception occurred! %d", ex.toString());
            } //end try-with-resources-catch
        } //end if 
    }

    /**
     * Loops a {@code .wav}, {@code .au}, or {@code .aiff} audio file for the
     * specified number of times by repeatedly
     * buffering and playing it.
     *
     * This method makes use of {@code bufferedPlay(java.lang.String)}. This
     * method is not guaranteed to work with files which do not follow the
     * {@code .wav}, {@code .aiff}, or {@code .au} file formats.
     *
     * @param filePath A {@code String} specifying the audio file to be played.
     * @param points   The number of times to loop the file. If 0, loops
     * continuously.
     *
     * @see #bufferedPlay(java.lang.String)
     * @see #bufferedLoop(java.lang.String, int)
     * @see #preloadedLoop(java.lang.String)
     * @see #preloadedLoop(java.lang.String, int)
     */
    public static void bufferedLoop(final String filePath, final int points) {

        //get 'File' object representation of String file path
        Path path = Paths.get(filePath);
        File file = path.toFile();

        //if path is correct, file is audio, etc... (see the 'isFileSupported(file)' 
        //method)
        if (isFileSupported(file)) {

            //if the number of times to be looped is 0, then loop continuously
            if (points == 0) {
                while (true) {
                    bufferedPlay(filePath);
                } //end while
            } else {
                //loop for specified times
                int counter = 0;
                while (counter < points) {
                    bufferedPlay(filePath);
                    counter++;
                } //end while
            } //end if-else
        } //end if
    }

    /**
     * Loops a {@code .wav}, {@code .au}, or {@code .aiff} audio file forever by
     * repeatedly buffering and playing it.
     *
     * @param filePath A {@code String} specifying the audio file to be played.
     *
     * @see #bufferedPlay(java.lang.String)
     * @see #bufferedLoop(java.lang.String, int)
     * @see #preloadedLoop(java.lang.String, int)
     * @see #preloadedLoop(java.lang.String)
     */
    public static void bufferedLoop(String filePath) {
        bufferedLoop(filePath, 0);
    }

    /**
     * Emits the system default audio beep.
     */
    public static void beep() {
        java.awt.Toolkit.getDefaultToolkit().beep();
    }
}
