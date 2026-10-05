package projectc.mod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LooseStoneBlock extends Block {

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

    private static final VoxelShape TOP_LEFT_SHAPE = Shapes.or(
            Block.box(2.5, 0, 3, 5.5, 1.5, 5),
            Block.box(3, 0, 2, 4.5, 1, 3),
            Block.box(3.8, 0, 5, 5.2, 0.7, 6)
    );

    private static final VoxelShape TOP_RIGHT_SHAPE = Shapes.or(
            Block.box(10.5, 0, 3, 13.5, 1.5, 5),
            Block.box(11, 0, 2, 12.5, 1, 3),
            Block.box(11.8, 0, 5, 13.2, 0.7, 6)
    );

    private static final VoxelShape BOTTOM_LEFT_SHAPE = Shapes.or(
            Block.box(2.5, 0, 11, 5.5, 1.5, 13),
            Block.box(3, 0, 10, 4.5, 1, 11),
            Block.box(3.8, 0, 13, 5.2, 0.7, 14)
    );

    private static final VoxelShape BOTTOM_RIGHT_SHAPE = Shapes.or(
            Block.box(10.5, 0, 11, 13.5, 1.5, 13),
            Block.box(11, 0, 10, 12.5, 1, 11),
            Block.box(11.8, 0, 13, 13.2, 0.7, 14)
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

    public static final net.minecraft.world.level.block.state.properties.EnumProperty<LooseStonePosition> POSITION =
            net.minecraft.world.level.block.state.properties.EnumProperty.create(
                    "position",
                    LooseStonePosition.class
            );

    @Override
    protected void createBlockStateDefinition(
            net.minecraft.world.level.block.state.StateDefinition.Builder<Block, BlockState> builder
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
        return getPositionShape(state);
    }

    @Override
    protected VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context
    ) {
        return getPositionShape(state);
    }

    private static VoxelShape getPositionShape(BlockState state) {
        return switch (state.getValue(POSITION)) {
            case TOP_LEFT -> TOP_LEFT_SHAPE;
            case TOP_RIGHT -> TOP_RIGHT_SHAPE;
            case BOTTOM_LEFT -> BOTTOM_LEFT_SHAPE;
            case BOTTOM_RIGHT -> BOTTOM_RIGHT_SHAPE;
            case MIDDLE -> STONE_SHAPE;
        };
    }
}