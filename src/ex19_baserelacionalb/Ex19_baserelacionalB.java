package ex19_baserelacionalb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ex19_baserelacionalB {

    /*
     a partir da taboa produtos creada no exerciio anterior e usando un resultset
     de tipo scrollable, updatable que devolte todas as 
     filas da taboa , desenvolver catro metodos... :
     */
    /*
     IMPORTANTE: a consulta de todos os campos dunha fila debe facerse explicitando
     o nome da tabao antes do * , e decir:  select produtos.* from produtos . . .      */
    Connection conn;

    public void Conexion() throws SQLException {

        String driver = "jdbc:oracle:thin:";
        String host = "localhost.localdomain"; // tambien puede ser una ip como "192.168.1.14"
        String porto = "1521";
        String sid = "orcl";
        String usuario = "hr";
        String password = "hr";
        String url = driver + usuario + "/" + password + "@" + host + ":" + porto + ":" + sid;

        conn = DriverManager.getConnection(url);

    }

    public void Cerrar() throws SQLException {

        conn.close();
    }

    /*
     - listar o contido completo do resultset
     */
    public void listarRS() throws SQLException {

        //Para crear un ResultSet de tipo "scrollable" y "updatable":
        Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

        ResultSet rs = st.executeQuery("select produtos.* from produtos");
        //Resultset.next() ya devuelve un bool si hay siguiente elemento, por lo 
        //que podemos hacer que compruebe directamente si hay siguiente elemento o no
        System.out.println("Codigo\tDescricion\tPrezo");
        while (rs.next()) {
            for (int col = 1; col <= 3; col++) {
                System.out.print(rs.getString(col) + "\t");

            }
            System.out.println();

        }
        rs.close();
    }

    /*
     - actualizar dende dentro do resultet : por exemplo a fila do producto p2 
     facendo que o seu precio pase a ser 8
     */
    //SOLO SE ACTUALIZA EL PRECIO
    public void actuRegistroRS(String codigo, int novoPrezo) {

        try {
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //cogemos todos los datos de la tabla y trabajamos solo con el ResultSet
            ResultSet rs = st.executeQuery("select produtos.* from produtos");

            while (rs.next()) {
                System.out.println(rs.getString("codigo"));

                //PODEMOS USAR EL MÉTODO getString(POSICION) PARA IR A LA COLUMNA QUE QUERAMOS
                //NEXT() SE MUEVE POR LAS FILAS !!!
                //NO SE PUEDEN COMPARAR 2 STRINGS CON "=="
                if (rs.getString(1).equals(codigo)) {
                    rs.updateInt("prezo", novoPrezo);
                    rs.updateRow();

                }

            }
            System.out.println("Actualizado !");
        } catch (SQLException ex) {
            Logger.getLogger(Ex19_baserelacionalB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error actualizando ");

        }

    }

    /*
     inserir dende dentro do resultset unha fila de valores : por exemplo o produto  p4, martelo, 20
     */
    public void insertarFilaRS(String cod, String desc, int prezo) {

        try {
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //cogemos todos los datos de la tabla y trabajamos solo con el ResultSet
            ResultSet rs = st.executeQuery("select produtos.* from produtos");
            rs.moveToInsertRow();
            rs.updateString("codigo", cod);
            rs.updateString("descricion", desc);
            rs.updateInt("prezo", prezo);

            rs.insertRow();

            System.out.println("Fila insertada !");

        } catch (SQLException ex) {
            Logger.getLogger(Ex19_baserelacionalB.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("ERROR INSERTANDO");
        }

    }

    public void borrarFilaRS(String cod) {

        try {

            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

            //cogemos todos los datos de la tabla y trabajamos solo con el ResultSet
            ResultSet rs = st.executeQuery("select produtos.* from produtos");
            //YA HACEMOS EN EL WHILE EN NEXT()
            while (rs.next()) {

                if (rs.getString(1).equalsIgnoreCase(cod)) {

                    rs.deleteRow();
                    break;

                }

            }
            System.out.println("FILA borrada");

        } catch (SQLException ex) {
            Logger.getLogger(Ex19_baserelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void main(String[] args) {

        Ex19_baserelacionalB obj = new Ex19_baserelacionalB();

        try {
            obj.Conexion();

//            obj.listarRS();
//            obj.actuRegistroRS("p2", 888);
//            obj.listarRS();
//            obj.insertarFilaRS("p66", "hola", 666);
//            obj.listarRS();
//
//            obj.borrarFilaRS("p3");
//            obj.listarRS();
            obj.Cerrar();
        } catch (SQLException ex) {
            Logger.getLogger(Ex19_baserelacionalB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
