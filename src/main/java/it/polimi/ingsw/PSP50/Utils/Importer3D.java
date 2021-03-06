package it.polimi.ingsw.PSP50.Utils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import org.fxyz3d.importers.Importer;
import org.fxyz3d.importers.ImporterFinder;
import org.fxyz3d.importers.Model3D;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Objects;
import java.util.ServiceLoader;

/**
 * The class is an improvement of the one of the library Fxyz3d:
 * It is used to call the customized version of the loadAsPoly method in the ObjImporter class.
 */
public final class Importer3D {

    /**
     * Get array of extension filters for supported file formats.
     *
     * @return array of extension filters for supported file formats.
     */
    public static String[] getSupportedFormatExtensionFilters() {
        return new String[]{"*.ma", "*.ase", "*.obj", "*.fxml", "*.dae"};
    }

    /**
     * Load a 3D file, always loaded as TriangleMesh.
     *
     * @param fileUrl The url of the 3D file to load
     * @return The loaded Node which could be a MeshView or a Group
     * @throws IOException if issue loading file
     */
    public static Model3D load(URL fileUrl) throws IOException {
        return loadIncludingAnimation(fileUrl, false);
    }

    /**
     * Load a 3D file, load as a PolygonMesh if the loader supports.
     *
     * @param fileUrl The url of the 3D file to load
     * @return The loaded Node which could be a MeshView or a Group
     * @throws IOException if issue loading file
     */
    public static Model3D loadAsPoly(URL fileUrl) throws IOException {
        return loadIncludingAnimation(fileUrl, true);
    }

    /**
     * Load a 3D file.
     *
     * @param fileUrl The url of the 3D file to load
     * @param asPolygonMesh When true load as a PolygonMesh if the loader supports
     * @return The loaded Node which could be a MeshView or a Group and the Timeline animation
     * @throws IOException if issue loading file
     */
    private static Model3D loadIncludingAnimation(URL fileUrl, boolean asPolygonMesh) throws IOException {
        Objects.requireNonNull(fileUrl, "URL must not be null");

        String extForm = fileUrl.toExternalForm();

        // get extension
        final int dot = extForm.lastIndexOf('.');
        if (dot <= 0) {
            throw new IOException("Unknown 3D file format, url missing extension [" + fileUrl + "]");
        }
        final String extension = extForm.substring(dot + 1).toLowerCase();
        // Reference all the importer jars
        ImporterFinder finder = new ImporterFinder();
        URLClassLoader classLoader = finder.addUrlToClassPath();

        ServiceLoader<Importer> servantLoader = ServiceLoader.load(Importer.class, classLoader);
        // Check if we have an implementation for this file type
        Importer importer = null;
        for (Importer plugin : servantLoader) {
            if (plugin.isSupported(extension)) {
                importer = plugin;
                break;
            }
        }

        // Check well known loaders that might not be in a jar (ie. running from an IDE)
        if ((importer == null) && (!extension.equals("fxml"))){
            String [] names = {
                    "org.fxyz3d.importers.dae.DaeImporter",
                    "org.fxyz3d.importers.max.MaxLoader",
                    "org.fxyz3d.importers.maya.MayaImporter",
                    "it.polimi.ingsw.PSP50.Utils.ObjImporter",
            };
            boolean fail = true;
            for (String name : names) {
                try {
                    Class<?> clazz = Class.forName(name);
                    Object obj = clazz.getDeclaredConstructor().newInstance();
                    if (obj instanceof Importer) {
                        Importer plugin = (Importer) obj;
                        if (plugin.isSupported(extension)) {
                            importer = plugin;
                            fail = false;
                            break;
                        }
                    }
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                        NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
                    // FAIL SILENTLY
                }
            }
            if (fail) throw new IOException("Unknown 3D file format [" + extension + "]");
        }

        if (extension.equals("fxml")) {
            final Object fxmlRoot = FXMLLoader.load(fileUrl);

            Model3D model = new Model3D();

            if (fxmlRoot instanceof Node) {
                model.addMeshView("default", (Node) fxmlRoot);
                return model;
            } else if (fxmlRoot instanceof TriangleMesh) {
                model.addMeshView("default", new MeshView((TriangleMesh) fxmlRoot));
                return model;
            }

            throw new IOException("Unknown object in FXML file [" + fxmlRoot.getClass().getName() + "]");
        } else {
            return asPolygonMesh ? importer.loadAsPoly(fileUrl) : importer.load(fileUrl);
        }
    }
}
