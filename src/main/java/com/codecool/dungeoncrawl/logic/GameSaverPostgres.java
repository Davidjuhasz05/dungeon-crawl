import com.codecool.dungeoncrawl.logic.CellDaoJdbc;

public class GameSaverPostgres {
    private final CellDaoJdbc cellDao;
    private final ActorDaoJdbc actorDao;
    private final ItemDaoJdbc itemDao;

    public GameSaverPostgres(CellDaoJdbc cellDao, ActorDaoJdbc actorDao, ItemDaoJdbc itemDao) {
        this.cellDao = cellDao;
        this.actorDao = actorDao;
        this.itemDao = itemDao;
    }

    public void saveMap(GameMap map) {
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Cell cell = map.getCell(x, y);
                if (cell.getItem() != null) {
                    itemDao.saveItem(cell.getItem());
                }
                if (cell.getActor() != null) {
                    actorDao.saveActor(cell.getActor());
                }
                cellDao.saveCell(cell, x, y);
            }
        }
    }
}
