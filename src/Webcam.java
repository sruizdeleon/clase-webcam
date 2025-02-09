/**
 * La clase Webcam representa una cámara web con una resolución (Alto x Ancho), número de FPS, estado de encendido/apagado y peso de captura de imágenes.
 * Proporciona métodos para realizar operaciones como: encender y apagar la cámara, tomar una foto, calcular el uso de datos de una videollamada, cambiar la resolucion y tomar una foto en tiempo real.
 *
 * Ejemplo de uso:
 * <pre>
 *    Webcam webcamPortatil = new Webcam("1920x1080", 60); // Crea una Webcam de resolucion 1920 ancho, 1080 alto y 60 fps, estado apagado.
 *    Webcam webcamSobremesa = new Webcam("1280x720", 30); // Crea una Webcam de resolucion 1290 ancho, 720 alto y 30 fps, estado apagado.
 *    webcamPortatil.encender(); // Cambia el estado a encendida
 *    webcamPortatil.apagar(); // Cambia el estado a apagado
 *    webcamSobremesa.encender();// Cambia el estado a encendido
 *    webcamSobremesa.tomarFoto(); // Devuelve un mensaje con la resolución y el tamaño de la foto.
 *    webcamSobremesa.consumoDeDatos(300); // Devuelve un mensaje con los datos gastados en una videollamada de 300 segundos.
 *    webcamSobremesa.cambiarResolucionYTomarFoto("2560x1440"); // Cambia la resolución a 2560 ancho, 1440 alto, recalcula el peso de una Foto y realiza una Foto.
 * </pre>
 *
 * La clase asegura que se respete el formato de resolución AnchoxAlto (Ej: 1920x1080), que los FPS sean un entero positivo y que no se puedan tomar fotos con la cámara apagada.
 *
 * Métodos disponibles:
 * - Constructor para crear webcam con parámetros de resolucion y fps.
 * - Métodos getter y setter para resolucion y fps.
 * - Métodos para encender, apagar la cámara.
 * - Métodos para tomar foto y cambiar resolución y tomar foto.
 * - Método para calcular el consumo de datos de una videollamada.
 *
 * Excepciones:
 * - IllegalArgumentException: Se lanza si se introduce un formato incorrecto en resolucion o fps.
 * - Exception:
 *
 * @author Sergio
 * @version 1.0
 * @since 2025-02-08
 */


public class Webcam {

  // ATRIBUTOS

  private String resolucion;
  private int fps;
  private boolean estado;
  private double pesoImagen;


  // CONSTRUCTOR

  /**
   * Constructor que recibe un string con la resolución y un entero con los FPS e inicia el estado de la cámara apagada.
   *
   * @param resolucion string en con formato AnchoxAlto (Ej: 1920x1080)
   * @param fps entero positivo (Ej: 30)
   */
  public Webcam(String resolucion, int fps) {
    if(!validacionResolucion(resolucion))
      throw new IllegalArgumentException("ERROR: Formato de resolucion no válido.");
    this.resolucion = resolucion;

    if(!validacionFPS(fps))
      throw new IllegalArgumentException("ERROR: Formato de fps no válido.");
    this.fps = fps;

    this.estado = false;

    // Establecemos el peso de una imagen
    establecerPesoImagen();
  }




  // VALIDADORES

  /**
   * Método de validación que recibe un string de resolución y valida que esté introducida en el formato correcto de AnchoxAlto.
   *
   * @param resolucion string en con formato AnchoxAlto (Ej: 1920x1080)
   * @return devuelve un booleano
   */
  private boolean validacionResolucion(String resolucion) {
    return resolucion.matches("\\d+x\\d+");
  }

  /**
   * Método de validación que recibe un entero de FPS y valida que sea positivo.
   *
   * @param fps entero positivo (Ej: 30)
   * @return devuelve un booleano
   */
  private boolean validacionFPS(int fps) {
    return (fps >= 0);
  }




  // GETTER Y SETTER

  /**
   * Establece una nueva resolución de cámara y recalcula el peso de una imagen.
   *
   * @param resolucion string en con formato AnchoxAlto (Ej: 1920x1080)
   */
  public void setResolucion(String resolucion) {
    if(!validacionResolucion(resolucion))
      throw new IllegalArgumentException("ERROR: Formato de resolucion no válido.");
    this.resolucion = resolucion;

    // Al cambiar la resolución de la cámara reestablecemos el peso de una imagen
    establecerPesoImagen();
  }

  /**
   * Obtiene la resolución de cámara actual.
   *
   * @return devuelve un string con formato AnchoxAlto
   */
  public String getResolucion() {
    return resolucion;
  }


  /**
   * Establece nuevos FPS de la cámara y recalcula el peso de una imagen.
   *
   * @param fps entero positivo (Ej: 30)
   */
  public void setFPS(int fps) {
    if(!validacionFPS(fps))
      throw new IllegalArgumentException("ERROR: Formato de FPS no válido.");
    this.fps = fps;

    // Al cambiar los FPS de la cámara reestablecemos el peso de una imagen
    establecerPesoImagen();
  }

  /**
   * Obtiene los FPS de la cámara actual.
   *
   * @return devuelve un entero positivo.
   */
  public int getFPS() {
    return fps;
  }





  // MÉTODOS

  /**
   * Método para establecer el peso de una imágen. No recibe parámetros ya que toma los atributos actuales de la cámara y lo guarda en el atributo pesoImagen.
   *
   */
  private void establecerPesoImagen() {
    int ancho = Integer.parseInt(resolucion.substring(0, resolucion.indexOf('x')));
    int alto = Integer.parseInt(resolucion.substring(resolucion.indexOf('x')+1, resolucion.length()));

    this.pesoImagen = (alto * ancho * fps) / 1000000.0;
  }

  /**
   * Método para encender la cámara poniendo el estado en true.
   */
  public void encender() {
    this.estado = true;
  }

  /**
   * Método para apagar la cámara poniendo el estado en false.
   */
  public void apagar() {
    this.estado = false;
  }

  /**
   * Método para tomar una foto.
   * Comprueba que la cámara esté encendida para poder realizar la captura y envía un mensaje con la resolucuón y el peso.
   *
   * @throws Exception Si la cámara está apagada lanza un error.
   */
  public void tomarFoto() {
    if(estado) {
      System.out.println("La foto fue tomada con éxito. Resolución: " + resolucion + " y Tamaño: " + pesoImagen + " MB");
    } else {
      throw new IllegalStateException("ERROR: estado de webcam apagada, no se puede hacer una foto.");
    }
  }

  /**
   * Método para cambiar la resolución, recalcular el tamaño de una foto y tomar una foto.

   * @param resolucion string en con formato AnchoxAlto (Ej: 1920x1080)
   */
  public void cambiarResolucionYTomarFoto(String nuevaResolucion) {
    try {
      setResolucion(nuevaResolucion);
      tomarFoto();
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Método para calcular el consumo de datos de una videollamada.
   * Recibe un parámetro segundos, de tipo entero, representando la duración de la llamada.
   * Muestra por pantalla el consumo en MB de la videollamada
   *
   * @param segundos entero duración de la llamada (Ej: 50)
   */
  public void consumoDeDatos(int segundos) {
    System.out.println("El consumo de una videollamda de "+segundos+" segundos es de "+(pesoImagen*segundos)+" MB.");
  }

  /**
   * Método para conocer todos los estados de la cámara.
   * Recibe un índice para determinar el paso del ejercicio y el objeto de cámara que queremos conocer el estado.
   *
   * @param indice entero representa el orden del paso
   * @param camara objeto de clase clase Webcam
   */
  public static void estadoCamara(int indice, Webcam camara) {
    System.out.println(indice + ". Cámara: Estado: " + (camara.estado ? "Encendida. ":"Apagada. ") + ", Resolución: " + camara.resolucion + ", FPS: " + camara.fps + " Peso imagen: " + camara.pesoImagen + " MB.");
  }



  // PROGRAMA

  public static void main(String[] args) {
    try {
      // Creamos el objeto webcamMiPortatil
      Webcam webcamMiPortatil = new Webcam("1920x1080", 30);
      estadoCamara(1, webcamMiPortatil);

      // Tomando foto
      // webcamMiPortatil.tomarFoto();
      // estadoCamara(2, webcamMiPortatil);

      // Encendiendo la cámara
      webcamMiPortatil.encender();
      estadoCamara(3, webcamMiPortatil);

      // Tomando foto
      webcamMiPortatil.tomarFoto();
      estadoCamara(4, webcamMiPortatil);

      // Apagando cámara
      webcamMiPortatil.apagar();
      estadoCamara(5, webcamMiPortatil);

      // Encendiendo cámara
      webcamMiPortatil.encender();
      estadoCamara(6, webcamMiPortatil);

      // Cambiar resolución y hacer foto
      webcamMiPortatil.cambiarResolucionYTomarFoto("1280x720");
      estadoCamara(7, webcamMiPortatil);

      // Calcular el consumo en videollamada
      webcamMiPortatil.consumoDeDatos(300);
      estadoCamara(8, webcamMiPortatil);

    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (IllegalStateException e) {
      System.out.println(e.getMessage());
    }
  }
}
