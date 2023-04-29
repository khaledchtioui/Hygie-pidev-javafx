/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hygie.entities;

import java.util.List;

/**
 *
 * @author Khaled
 */public class ResponseGroup {
    private String name;
    private List<Reponse> responses;

    public ResponseGroup(String name, List<Reponse> responses) {
        this.name = name;
        this.responses = responses;
    }

    public String getName() {
        return name;
    }

    public List<Reponse> getResponses() {
        return responses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setResponses(List<Reponse> responses) {
        this.responses = responses;
    }
}

