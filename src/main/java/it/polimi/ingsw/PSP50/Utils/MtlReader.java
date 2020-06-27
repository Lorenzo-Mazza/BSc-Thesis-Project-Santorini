package it.polimi.ingsw.PSP50.Utils;

import static java.util.Map.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;

    /**
     *  The class is an improvement of the one of the library Fxyz3d:
     *  It helps the other two classes Importer3D and ObjImporter
     */
    public class MtlReader {

        private String baseUrl;

        public MtlReader(String filename, String parentUrl) {
            baseUrl = parentUrl.substring(0, parentUrl.lastIndexOf('/') + 1);
            String fileUrl = baseUrl + filename;
            ObjImporter.log("Reading material from filename = " + fileUrl);
            try (Stream<String> line = Files.lines(Paths.get(new URI(fileUrl)))) {
                line.map(String::trim)
                        .filter(l -> !l.isEmpty() && !l.startsWith("#"))
                        .forEach(this::parse);
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }

        private Map<String, Material> materials = new HashMap<>();
        private PhongMaterial currentMaterial;
        private Set<String> readProperties = new HashSet<>(PARSERS.size() - 1);

        // mtl format spec: http://paulbourke.net/dataformats/mtl/
        private static final Map<String, BiConsumer<String, MtlReader>> PARSERS = Map.ofEntries(
                entry("newmtl ",    (l, m) -> m.parseNewMaterial(l)),
                // Material color and illumination
                entry("Ka ",        (l, m) -> m.parseIgnore("Ambient reflectivity (Ka)")),
                entry("Kd ",        (l, m) -> m.parseDiffuseReflectivity(l)),
                entry("Ks ",        (l, m) -> m.parseSpecularReflectivity(l)),
                entry("Ns ",        (l, m) -> m.parseSpecularExponent(l)),
                entry("Tf ",        (l, m) -> m.parseIgnore("Transmission filter (Tf)")),
                entry("illum ",     (l, m) -> m.parseIgnore("Illumination model (illum)")),
                entry("d ",         (l, m) -> m.parseIgnore("dissolve (d)")),
                entry("Tr ",        (l, m) -> m.parseIgnore("Transparency (Tr)")),
                entry("sharpness ", (l, m) -> m.parseIgnore("Sharpness (sharpness)")),
                entry("Ni ",        (l, m) -> m.parseIgnore("Optical density (Ni)")),
                // Material texture map
                entry("map_Ka ",    (l, m) -> m.parseIgnore("Ambient reflectivity map (map_Ka)")),
                entry("map_Kd ",    (l, m) -> m.parseDiffuseReflectivityMap(l)),
                entry("map_Ks ",    (l, m) -> m.parseSpecularReflectivityMap(l)),
                entry("map_Ns ",    (l, m) -> m.parseIgnore("Specular exponent map (map_Ns)")),
                entry("map_d ",     (l, m) -> m.parseIgnore("Dissolve map (map_d)")),
                entry("disp ",      (l, m) -> m.parseIgnore("Displacement map (disp)")),
                entry("decal ",     (l, m) -> m.parseIgnore("Decal stencil map (decal)")),
                entry("bump ",      (l, m) -> m.parseBumpMap(l)),
                entry("refl ",      (l, m) -> m.parseIgnore("Reflection map (refl)")),
                entry("map_aat ",   (l, m) -> m.parseIgnore("Anti-aliasing (map_aat)")));

        private void parse(String line) {
            for (Entry<String, BiConsumer<String, MtlReader>> parser : PARSERS.entrySet()) {
                String identifier = parser.getKey();
                if (line.startsWith(identifier)) {
                    if (!"newmtl ".equals(identifier) && !readProperties.add(identifier)) {
                        ObjImporter.log(identifier + "already read for current material. Ignoring.");
                        return;
                    }
                    parser.getValue().accept(line.substring(identifier.length()), this);
                    return;
                }
            }
            ObjImporter.log("No parser found for: " + line);
        }

        private void parseIgnore(String nameAndKey) {
            ObjImporter.log(nameAndKey + " is not supported. Ignoring.");
        }

        private void parseNewMaterial(String value) {
            if (materials.containsKey(value)) {
                ObjImporter.log(value + " material is already added. Ignoring.");
                return;
            }
            currentMaterial = new PhongMaterial();
            readProperties.clear();
            materials.put(value, currentMaterial);
            ObjImporter.log(System.lineSeparator() + "Reading material " + value);
        }

        private void parseDiffuseReflectivity(String value) {
            currentMaterial.setDiffuseColor(readColor(value));
        }

        private void parseSpecularReflectivity(String value) {
            currentMaterial.setSpecularColor(readColor(value));
        }

        private void parseSpecularExponent(String value) {
            currentMaterial.setSpecularPower(Double.parseDouble(value));
        }

        private void parseDiffuseReflectivityMap(String value) {
            currentMaterial.setDiffuseMap(loadImage(value));
        }

        private void parseSpecularReflectivityMap(String value) {
            currentMaterial.setSpecularMap(loadImage(value));
        }

        private void parseBumpMap(String value) {
            currentMaterial.setBumpMap(loadImage(value));
        }

        private Color readColor(String line) {
            String[] split = line.trim().split(" +");
            float red = Float.parseFloat(split[0]);
            float green = Float.parseFloat(split[1]);
            float blue = Float.parseFloat(split[2]);
            return Color.color(red, green, blue);
        }

        private Image loadImage(String filename) {
            filename = baseUrl + filename;
            ObjImporter.log("Loading image from " + filename);
            return new Image(filename);
        }

        public Map<String, Material> getMaterials() {
            return Collections.unmodifiableMap(materials);
        }
    }

