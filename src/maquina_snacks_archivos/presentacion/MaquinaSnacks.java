package maquina_snacks_archivos.presentacion;

import maquina_snacks_archivos.dominio.Snack;
import maquina_snacks_archivos.servicio.IServicioSnacks;
import maquina_snacks_archivos.servicio.ServicioSnacksArchivos;
import maquina_snacks_archivos.servicio.ServicioSnacksLista;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MaquinaSnacks {
    public static void main(String[] args) {
        maquinaSnacks();
    }

    public static void maquinaSnacks(){
        var salir = false; // ejecucion del ciclo while
        var consola = new Scanner(System.in);
        // Crear el objeto para obtener el servicio de Snacks (Lista)
        //IServicioSnacks servicioSnacks = new ServicioSnacksLista();
        IServicioSnacks servicioSnacks = new ServicioSnacksArchivos();
        // Creamos la lista de productos de tipo snack
        List<Snack> productos = new ArrayList<>();
        System.out.println("  MAQUINA DE SNACKS  ");
        servicioSnacks.mostrarSnacks();// Mostrar inventario de snacks disponibles

        while(!salir){
            try{
                var opcion = mostrarMenu(consola);
                salir= ejecutarOpciones(opcion, consola, productos, servicioSnacks);
            }catch(Exception e){
                System.out.println("Ocurrio un error: " + e.getMessage());
            }finally{
                System.out.println(); // Imprime un salto de linea con cada iteracion
            }
        }
    }
    private static int mostrarMenu(Scanner consola){
        System.out.print("""
                Menu:
                1. Comprar Snack
                2. Mostrar ticket
                3. Agregar nuevo Snack
                4. Salir
                Elige una opción: \s
                """);
        // Leemos y retornamos la opcion seleccionada
        return Integer.parseInt(consola.nextLine());
    }

    private static boolean ejecutarOpciones(int opcion, Scanner consola, List<Snack> productos, IServicioSnacks servicioSnacks){
        var salir = false;
        switch (opcion){
            case 1 -> comprarSnack(consola, productos, servicioSnacks);
            case 2 -> mostrarTicket(productos);
            case 3 -> agregarSnack(consola, servicioSnacks);
            case 4 -> {
                System.out.println("Regresa pronto!!!");
                salir=true;
            }
            default -> System.out.println("Opcion Invalida "+ opcion);
        }
        return salir;
    }

    private static void comprarSnack(Scanner consola, List<Snack> productos, IServicioSnacks servicioSnacks){
        System.out.println("Que snack quieres comprar: (id)");
        var idSnack = Integer.parseInt(consola.nextLine());
        //  Validar que el snack exista
        var snackEncontrado = false;
        for(var snack: servicioSnacks.getSnacks()){
            if(idSnack == snack.getIdSnack()){
                // Agregamos el snack a la lista de producots
                productos.add(snack);
                System.out.println("OK, Snack agregado: "+ snack);
                snackEncontrado = true;
                break;
            }
        }
        if(!snackEncontrado){
            System.out.println("Id de snack no encontrado: "+ idSnack);
        }
    }

    private static void mostrarTicket(List<Snack> productos){
        var ticket = "*** Ticket de venta ***";
        var total = 0.0;
        for(var producto: productos){
            ticket += "\n\t" + producto.getNombre() + " - $ "+producto.getPrecio();
            total += producto.getPrecio();
        }
        ticket += "\n\t Total -> $" + total;
        System.out.println(ticket);
    }

    private static void agregarSnack(Scanner consola, IServicioSnacks servicioSnacks){
        System.out.println("Nombre del Snack: ");
        var nombre = consola.nextLine();
        System.out.println("Precio del Snack: ");
        var precio = Double.parseDouble(consola.nextLine());
        servicioSnacks.agregarSnack(new Snack(nombre, precio));
        System.out.println("Tu snack se ha agregado correctamente");
        servicioSnacks.mostrarSnacks();

    }
}

