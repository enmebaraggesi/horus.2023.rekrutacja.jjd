package pl.com.horus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Wall implements Structure {
    
    private List<Block> blocks;
    
    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }
    
    // Niestety nie otrzymałem odpowiedzi na pytanie o oblicze CompositeBlock, wobec czego założyłem scenariusz,
    // w którym CompositeBlock'i są sprawdzane i mogą zawierać kolejne. W takim przypadku potrzebowałem spłaszczyć
    // strumień za pomocą rekurencji.
    // Opcjonalnie można dodać sprawdzenie null'a w filtrze.
    // W metodach można wprowadzić także Set do sprawdzania przeszukanych kompozytów, aby uniknąć ewentualnej pętli.
    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                     .flatMap(block -> block instanceof CompositeBlock compositeBlock ?
                             findBlocksInCompositeBlock(compositeBlock).stream() :
                             Stream.of(block))
                     .filter(block -> block.getColor().equals(color))
                     .findFirst();
    }
    
    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                     .flatMap(block -> block instanceof CompositeBlock compositeBlock ?
                             findBlocksInCompositeBlock(compositeBlock).stream() :
                             Stream.of(block))
                     .filter(block -> block.getMaterial().equals(material))
                     .toList();
    }
    
    @Override
    public int count() {
        return (int) blocks.stream()
                           .flatMap(block -> block instanceof CompositeBlock compositeBlock ?
                                   findBlocksInCompositeBlock(compositeBlock).stream() :
                                   Stream.of(block))
                           .count();
    }
    
    private List<Block> findBlocksInCompositeBlock(CompositeBlock compositeBlock) {
        return compositeBlock.getBlocks().stream()
                             .flatMap(block -> block instanceof CompositeBlock innerCompositeBlock ?
                                     findBlocksInCompositeBlock(innerCompositeBlock).stream() :
                                     Stream.of(block))
                             .toList();
    }
    
    public List<Block> getBlocks() {
        return blocks;
    }
}
