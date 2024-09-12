package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author Fabio
 */
public class Tocador {

    public static void tocaMacaco(){

        FileInputStream in;
 try {
  //Inicializa o FileInputStream com o endereço do arquivo para tocar
  in = new FileInputStream("C:\\Users\\labsfiap\\Documents\\NetBeansProjects\\teste_sons\\src\\sounds\\Angry Monkey - Sound Effect.mp3");

  //Cria uma instancia da classe player passando para ele o InpuStream do arquivo
  Player p = new Player(in);

  //executa o som
  p.play();
 } catch (FileNotFoundException e) {
  e.printStackTrace();
 } catch (JavaLayerException e) {
  e.printStackTrace();
 }


    }

}
