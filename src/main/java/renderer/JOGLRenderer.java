package renderer;

import board.Board;
import board.Tile;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;
import game.Game;
import pieces.Piece;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class JOGLRenderer implements GLEventListener {
    static private final int TEXTURE_HEIGHT = 1028;
    static private final int TEXTURE_WIDTH = 1028;
    static private final String TEXTURES_PATH = "textures/default";

    static private final int RANK_COUNT = 8;
    static private final int FILE_COUNT = 8;


    private Board board;
    private Game game;
    private List<Tile> highlightedTiles = new ArrayList<>();
    private GL2 gl;
    private final TextureLoader textures = new TextureLoader(TEXTURES_PATH); // may want to consider making this configurable

    void setGame(Game game) {
        this.game = game;
        this.board = game.getBoard();
    }

    void clear() { // mostly for aesthetics, and external usage
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    }


    private Texture getTextureForTile(Tile tile) {
        String baseName = "tile_" + tile.getColor().toString().toLowerCase();
        String endName = highlightedTiles.contains(tile) ? "_light" : "_dark";
        String name = baseName + endName;
        return textures.getTexture(name);
    }

    private Texture getTextureForPiece(Piece piece) {
        return textures.getTexture(piece.getTexture());
    }

    void drawBoard() {
        for (int rank = 0; rank < RANK_COUNT; rank++) {
            // order doesn't matter, as long as the coords are calculated correctly!
            for (int file = 0; file < FILE_COUNT; file++) {
                Tile tile = board.getTile(rank, file);
                Texture tex = getTextureForTile(tile);
                drawTexturedQuadBoardCoords(tex, file, rank);
            }
        }
    }

    void drawPieces() {
        // parse the board and draw pieces
        List<Tile> activeTiles = game.getPiecesOnBoard();
        for (Tile tile : activeTiles) {
            Piece piece = tile.getPiece();
            Texture tex = getTextureForPiece(piece);
            drawTexturedQuadBoardCoords(tex, tile.getRank(), tile.getFile());
        }

    }

    void drawTexturedQuadBoardCoords(Texture tex, int rank, int file) {
        drawTexturedQuad(tex, rank, file, 1, 1);
    }

    void drawTexturedQuad(Texture tex, float x, float y, float width, float height) {
        tex.enable(gl);
        tex.bind(gl);

        // texture coordinate, then vertex location
        gl.glBegin(GL2.GL_QUADS);

        gl.glTexCoord2f(0f, 0f);
        gl.glVertex2f(x, y);

        gl.glTexCoord2f(1f, 0f);
        gl.glVertex2f(x + width, y);

        gl.glTexCoord2f(1f, 1f);
        gl.glVertex2f(x + width, y + height);

        gl.glTexCoord2f(0f, 1f);
        gl.glVertex2f(x, y + height);

        gl.glEnd();

        tex.disable(gl);
    }


    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);
        //shaders shouldn't need for GL2. This is where we would load them
        textures.loadAll();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // this frees up resources, shouldn't need to do anything for our implementation
        // maybe unload textures and shaders?
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        if (board == null) return;
        drawBoard();
        drawPieces();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        gl.glViewport(0, 0, width, height);
        //2D orthographic projection, so only a little black magic needs to occur.

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        // this sets (0,0) as the bottom left corner, and (8,8) as the top right corner
        gl.glOrtho(0, 8.0,
                0, 8.0,
                -1, 1);
        // TODO: rotate 180 deg when the player is black
    }

    public void setHighlightedTiles(List<Tile> highlightedTiles) {
        this.highlightedTiles = highlightedTiles;
    }

    /// Loads all textures (rasterized image files) in the directory it is given (recursively).
    /// Maintains a map of textures, keyed to the filename without the extension for later use: "white_king.png" -> "white_king"
    /// Currently supports: png
    private class TextureLoader {
        private final String rootDir;
        private final Set<String> loadedTextures = new HashSet<>();
        private final Set<String> supportedExt;
        private final Map<String, Texture> textureMap = new HashMap<>();

        /// Path in src/main/resources ex: "textures"
        /// I only added support for pngs right now, but this is easily extendible to allow other rasterized file formats!
        public TextureLoader(String path) {
            this(path, Set.of("png")); // only support png unless specified otherwise
        }

        public TextureLoader(String path, Set<String> exts) {
            this.rootDir = normalizeRoot(path);
            this.supportedExt = exts;
        }

        private String normalizeRoot(String p) {
            // make sure the path ends with a '/' and doesn't start with one.
            String r = p;
            if (r.startsWith("/")) r = r.substring(1);
            if (r.endsWith("/")) r = r.substring(0, r.length() - 1);
            return r;
        }


        public void loadAll() {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            URL root = cl.getResource(rootDir);

            if (root == null) {
                throw new IllegalStateException("Root directory not found: " + rootDir);
            }

            if (!"file".equals(root.getProtocol())) {
                throw new IllegalStateException("Root directory must be a file path: " + rootDir);
            }

            File rootFile = new File(root.getPath());
            if (!rootFile.isDirectory()) {
                throw new IllegalStateException("Root directory must be a directory: " + rootDir);
            }
            scanDirectory(rootFile, rootDir);

        }

        /// This is recursive to allow "creative" file structures for textures.
        /// Each texture will be loaded under the final filename, so make sure those are unique!
        private void scanDirectory(File dir, String classpathPrefix) {
            File[] files = dir.listFiles();
            if (files == null) return;

            for (File file : files) {
                if (file.isDirectory()) {
                    String filePrefix = classpathPrefix + "/" + file.getName();
                    scanDirectory(file, filePrefix);
                } else {
                    String fileName = file.getName();
                    String fullpath = classpathPrefix + "/" + fileName;

                    // check that the file has a valid extension
                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex <= 0 || dotIndex >= fileName.length() - 1) {
                        throw new IllegalArgumentException("Invalid texture file name: " + fullpath);
                    }

                    // check that the extension is supported
                    String ext = fileName.substring(dotIndex + 1).toLowerCase();
                    if (supportedExt.contains(ext)) {
                        //if it is, load it
                        String key = fileName.substring(0, dotIndex);
                        loadTexture(fullpath, key);
                    } else {
                        // else throw exception
                        throw new IllegalArgumentException("Unsupported texture file extension: " + fullpath);
                    }
                }
            }
        }

        private void loadTexture(String resourcePath, String key) {
            try (InputStream is = getClass().getResourceAsStream(resourcePath)) {
                if (is == null) {
                    throw new IllegalStateException("Texture not found: " + resourcePath);
                }
                if (loadedTextures.contains(key)) throw new IllegalStateException("Duplicate texture key: " + key);

                Texture texture = TextureIO.newTexture(is, false, "png");//this is where we would extend to support other formats
                textureMap.put(key, texture);
                loadedTextures.add(key);

            } catch (IOException e) {
                throw new RuntimeException("Failed to load texture: ", e);
            }
        }

        public Texture getTexture(String key) {
            return textureMap.get(key);
        }

        public Set<String> getLoadedTextures() {
            return loadedTextures;
        }

        public Map<String, Texture> getAll() {
            return Collections.unmodifiableMap(textureMap);
        }
    }

    private static class LowLevelRenderer {

    }
}
