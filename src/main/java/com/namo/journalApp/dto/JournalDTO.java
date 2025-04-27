package com.namo.journalApp.dto;

import java.time.LocalDateTime;


import com.namo.journalApp.entities.Journal;

import lombok.Data;
@Data
public class JournalDTO {
    private String id;
    private String title;
    private String content;
    private LocalDateTime time;

    public JournalDTO(Journal journal){
        this.id=journal.getId().toHexString();
        this.title=journal.getTitle();
        this.content=journal.getContent();
        this.time=journal.getTime();
    }
}
