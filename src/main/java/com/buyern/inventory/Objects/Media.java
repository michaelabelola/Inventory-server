package com.buyern.inventory.Objects;

import com.buyern.inventory.Enums.SimpleMediaType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Media {
   private String id;
    private String name;
    private String tag;
    @Enumerated(EnumType.STRING)
    private SimpleMediaType type;
    private String link;
}