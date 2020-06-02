/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_brainsDuel
 * Autor: Equipo Cupi2 2016
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package uniandes.cupi2.brainsDuel.cliente.mundo;

/**
 * Clase que representa una pregunta
 * @author Cupi2
 *
 */
public class Pregunta
{

    /**
     * Categor�a de la pregunta
     */
    private String categoria;

    /**
     * Texto de la pregunta
     */
    private String pregunta;

    /**
     * Opciones de la pregunta
     */
    private String[] opciones;

    /**
     * Respuesta del usuario a la pregunta
     */
    private String respuestaUsuario;

    /**
     * Construye una nueva pregunta
     * @param pCategoria Categor�a
     * @param pPregunta Pregunta
     * @param pOpciones Opciones
     */
    public Pregunta( String pCategoria, String pPregunta, String[] pOpciones )
    {
        categoria = pCategoria;
        pregunta = pPregunta;
        opciones = pOpciones;
        respuestaUsuario = null;
    }

    /**
     * Retorna la categor�a de la pregunta
     * @return Categor�a de la pregunta
     */
    public String darCategoria( )
    {
        return categoria;
    }

    /**
     * Retorna el texto de la pregunta
     * @return Texto de la pregunta
     */
    public String darPregunta( )
    {
        return pregunta;
    }

    /**
     * Opciones de respuesta para la pregunta
     * @return Opciones de respuesta
     */
    public String[] darOpciones( )
    {
        return opciones;
    }

    /**
     * Respuesta del usuario
     * @return Respuesta del usuario
     */
    public String darRespuestaUsuario( )
    {
        return respuestaUsuario;
    }

    /**
     * Cambia la respuesta del usuario
     * @param pRespuesta Nueva respuesta
     */
    public void cambiarRespuesta( String pRespuesta )
    {
        respuestaUsuario = pRespuesta;
    }

}
