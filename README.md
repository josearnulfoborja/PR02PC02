# PR02PC02

Este proyecto es una aplicación Java basada en Jakarta EE.

## Requisitos previos
- Java JDK 8 o superior
- Apache Maven
- Un servidor compatible con Jakarta EE (por ejemplo, Payara, GlassFish, TomEE)
- Visual Studio Code (VS Code)
- Extensión de Java para VS Code (recomendado: [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack))

## Ejecución en VS Code

1. **Clona el repositorio**
   ```sh
git clone https://github.com/josearnulfoborja/PR02PC02.git
cd PR02PC02
```

2. **Abre la carpeta en VS Code**
   - Selecciona "File" > "Open Folder..." y elige la carpeta del proyecto.

3. **Instala las extensiones recomendadas**
   - Si VS Code lo sugiere, instala las extensiones recomendadas para Java.

4. **Compila el proyecto**
   - Abre la terminal integrada (`Ctrl+ñ` o `Ctrl+Shift+ñ` en Windows).
   - Ejecuta:
     ```sh
     mvn clean package
     ```
   - El archivo `.war` generado estará en la carpeta `target/`.

5. **Despliega en un servidor Jakarta EE**
   - Copia el archivo `.war` a la carpeta de despliegue de tu servidor (por ejemplo, `payara5/glassfish5/domains/domain1/autodeploy`).
   - Inicia el servidor si no está en ejecución.

6. **Accede a la aplicación**
   - Abre tu navegador y navega a la URL correspondiente, por ejemplo:
     ```
     http://localhost:8080/PR02PC02/
     ```

## Notas adicionales
- Puedes editar el código fuente en `src/main/java/com/mycompany/pr02pc02/`.
- Los recursos web (HTML, XML) están en `src/main/webapp/`.
- El archivo de configuración de Maven es `pom.xml`.
- Si necesitas persistencia, revisa `src/main/resources/META-INF/persistence.xml`.
- Para pruebas, puedes agregar clases en `src/test/java/`.

## Consejos
- Usa la paleta de comandos (`Ctrl+Shift+P`) y busca "Java: Create Java Project" o "Maven: Compile" para tareas rápidas.
- Si tienes problemas con dependencias, ejecuta `mvn clean install`.
- Consulta la documentación oficial de Jakarta EE y Maven para más detalles.

---

**Si tienes NetBeans, puedes abrir el proyecto directamente como un proyecto Maven.**

---

> _Este README está orientado a usuarios de Visual Studio Code que no cuentan con NetBeans._
