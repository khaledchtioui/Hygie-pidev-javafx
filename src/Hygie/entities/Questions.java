package hygie.entities;



public class Questions {
    private int id ;
    private String question ;
    private int type ;
    private int point   ;
    private Questionnaire quizid ;

    public Questions(int id, String question, int type, int point, Questionnaire quizid) {
        this.id = id;
        this.question = question;
        this.type = type;
        this.point = point;
        this.quizid = quizid;
    }

    public Questions(int id) {
        this.id = id;
    }

    public Questions(String question, int type, int point, Questionnaire quizid) {
        this.question = question;
        this.type = type;
        this.point = point;
        this.quizid = quizid;
    }

    @Override
    public String toString() {
        return "Questions{" + "question=" + question + ", type=" + type + ", point=" + point + ", quizid=" + quizid + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Questionnaire getQuizid() {
        return quizid;
    }

    public void setQuizid(Questionnaire quizid) {
        this.quizid = quizid;
    }

    public Questions(int id, String question) {
        this.id = id;
        this.question = question;
    }

    //
    
    //

}
