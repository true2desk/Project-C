package projectc.mod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LooseStoneBlock extends Block {

    public static final EnumProperty<LooseStonePosition> POSITION =
            EnumProperty.create("position", LooseStonePosition.class);

    private static final VoxelShape STONE_1 = Block.box(
            6.5, 0, 7,
            9.5, 1.5, 9
    );

    private static final VoxelShape STONE_2 = Block.box(
            7, 0, 6,
            8.5, 1, 7
    );

    private static final VoxelShape STONE_3 = Block.box(
            7.8, 0, 9,
            9.2, 0.7, 10
    );

    private static final VoxelShape STONE_SHAPE = Shapes.or(
            STONE_1,
            STONE_2,
            STONE_3
    );

    public LooseStoneBlock(Properties properties) {
        super(properties);

        registerDefaultState(
                stateDefinition.any().setValue(
                        POSITION,
                        LooseStonePosition.MIDDLE
                )
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(POSITION);
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return STONE_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return STONE_SHAPE;
    }
}