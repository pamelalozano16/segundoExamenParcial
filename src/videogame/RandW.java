package videogame;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Pamela Lozano
 */
public class RandW {

    private Game g;
    //En game hice todo publico para editarlo mas facil

    public RandW(Game g) {
        this.g = g;
    }

    /* ORDERN DE LA INFORMACION:
    -vidas
    -score
    -player x/y
    -direccion de los enemigos
    -enemigos x/y
    -si ese enemigo esta vivo
     */
    public void Save(String fileName) {
        try {
            String info = ""; //Aqui se va guardando la informacion
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            //Direction de un enemy de la lista
            int directionEnemys = g.enemys.get(0).getDirection();
            
            info += ("" + g.lives + "/" + g.score + "/" + g.player.getX() + "/" + g.player.getY());
            info += ("/" + directionEnemys);
            
            for (Enemy enemy : g.enemys) {
                int visible=0;
                if(enemy.isVisible()){
                    visible=1;
                }
                info += ("/" + enemy.getX() + "/" + enemy.getY());
                info += ("/"+visible);
            }
            /*info += ("/" + g.tamBuenos);
            for (Good good : g.pacmans) {
                info += ("/" + good.getX() + "/" + good.getY());
            }*/
            writer.println(info);
            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not found CALL 911");
        }
    }

    public void Load(String strFileName) {
        try {
            int j, i;
            int direction;
            
            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            line = reader.readLine();
            datos = line.split("/");
            g.lives = Integer.parseInt(datos[0]); //load Vidas
            g.score = Integer.parseInt(datos[1]); //load Score
            g.player.setX(Integer.parseInt(datos[2]));//load player x
            g.player.setY(Integer.parseInt(datos[3]));//load player y
            direction = Integer.parseInt(datos[4]);//load direccion

            for (i = 5, j = 1; j <= 24; i += 3, j++) { //Enemigos
                int x = Integer.parseInt(datos[i]);//load enemy x
                int y = Integer.parseInt(datos[i + 1]);//load enemy y
                int visible = Integer.parseInt(datos[i + 2]);//Load si es visible
                Enemy enemy = new Enemy(x, y, direction, 15, 15, g);
                if(visible==0){
                enemy.die();//Si no es visible die
                }
                g.enemys.add(enemy); //Se agrega a la lista
            }

            System.out.println("Se leyo  vidas = " + g.lives );
            reader.close();
        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }
}
