package pl.com.horus;

import java.util.List;

public interface CompositeBlock extends Block {
    
    List<Block> getBlocks();
}
