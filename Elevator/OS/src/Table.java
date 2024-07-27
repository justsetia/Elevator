public class Table {
    private String TableFirstColumn;
    private String TableSecondColumn;
    private String TableThirdColumn;
    private String TableFourthColumn;
    private String TableFifthColumn;

    public Table(String TableFirstColumn,String TableSecondColumn, String TableThirdColumn, String TableFourthColumn,
                 String TableFifthColumn){
        this.TableFirstColumn=TableFirstColumn;
        this.TableSecondColumn=TableSecondColumn;
        this.TableThirdColumn = TableThirdColumn;
        this.TableFourthColumn= TableFourthColumn;
        this.TableFifthColumn=TableFifthColumn;
    }

    public String getTableFirstColumn(){
        return TableFirstColumn;
    }
    public String getTableSecondColumn(){
        return TableSecondColumn;
    }
    public String getTableThirdColumn(){
        return TableThirdColumn;
    }
    public String getTableFourthColumn(){
        return TableFourthColumn;
    }
    public String getTableFifthColumn(){
        return TableFifthColumn;
    }


}
