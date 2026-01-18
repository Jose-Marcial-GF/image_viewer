package software.ulpgc.imageviewer.architecture.io;

import software.ulpgc.imageviewer.architecture.model.Image;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public record ImageProvider(List<String> images) {

    public static ImageProvider with(Stream<String> images) {
        return new ImageProvider(images.toList());
    }

    public Image first(Function<String, byte[]> loader) {
        return load(0, loader);
    }

    private Image load(int index, Function<String, byte[]> loader) {
        return new Image() {
            private final int size = images.size();
            private byte[] bitmap;

            @Override
            public String id() {
                return images.get(index);
            }

            @Override
            public byte[] bitmap() {
                if (bitmap == null) {
                    bitmap = loader.apply(id());
                }
                return bitmap;
            }

            @Override
            public Image next() {
                return load(nextIndex(), loader);
            }

            private int nextIndex() {
                return (index + 1) % size;
            }

            @Override
            public Image previous() {
                return load(prevIndex(), loader);
            }

            private int prevIndex() {
                return (index - 1 + size) % size;
            }
        };
    }
}
