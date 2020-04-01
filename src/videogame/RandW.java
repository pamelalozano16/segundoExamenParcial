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
    -tamaño Malos
    -enemigos x/y
    -tamaño Buenos
    -buenos x/y
     */
    public void Save(String fileName) {
        try {
            String info = ""; //Aqui se va guardando la informacion
            PrintWriter writer = new PrintWriter(new FileWriter(fileName));
            info += ("" + g.lives + "/" + g.score + "/" + g.player.getX() + "/" + g.player.getY());
            info += ("/" + g.tamMalos);
            for (Enemy enemy : g.enemys) {
                info += ("/" + enemy.getX() + "/" + enemy.getY());
            }
            info += ("/" + g.tamBuenos);
            for (Good good : g.pacmans) {
                info += ("/" + good.getX() + "/" + good.getY());
            }
            writer.println(info);
            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not found CALL 911");
        }
    }

    public void Load(String strFileName) {
        try {
            int j, i;

            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            line = reader.readLine();
            datos = line.split("/");
            g.lives = Integer.parseInt(datos[0]); //load Vidas
            g.score = Integer.parseInt(datos[1]);//load score
            g.player.setX(Integer.parseInt(datos[2]));//load player x
            g.player.setY(Integer.parseInt(datos[3]));//load player y
            g.tamMalos = Integer.parseInt(datos[4]);//load tamaño de malos

            for (i = 5, j = 1; j <= g.tamMalos; i += 2, j++) { //Enemigos
                int x = Integer.parseInt(datos[i]);//load enemy x
                int y = Integer.parseInt(datos[i + 1]);//load enemy y
                Enemy enemy = new Enemy(x, y, 1, 50, 50, g);
                g.enemys.add(enemy); //Se agrega a la lista
            }

            g.tamBuenos = Integer.parseInt(datos[i]);
            i++;
            for (j = 1; j <= g.tamBuenos; i += 2, j++) { //Monedas
                int x = Integer.parseInt(datos[i]);//load moneda x
                int y = Integer.parseInt(datos[i + 1]);//load moneda y
                Good good = new Good(x, y, 1, 35, 50, g);
                g.pacmans.add(good); //Se agrega a la lista
            }

            System.out.println("Se leyo  vidas = " + g.lives + " y score = " + g.score + " tamMalos = "
                    + g.tamMalos + " tamBuenos = " + g.tamBuenos);
            reader.close();
        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }
}
