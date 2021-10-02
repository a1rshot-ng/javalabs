package org.airng.labsecond.service;

import org.airng.labsecond.base.PersonType;
import org.airng.labsecond.base.Subject;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class CommandMixIn {
  public CommandMixIn(@JsonProperty("action") String action,
                      @JsonProperty("id") int id,
                      @JsonProperty("personType") PersonType personType,
                      @JsonProperty("fullName") String fullName,
                      @JsonProperty("birthDate") Date birthDate,
                      @JsonProperty("phoneNumber") String phoneNumber,
                      @JsonProperty("studentSubjects") List<Subject> studentSubjects,
                      @JsonProperty("studentMarks") Map<Subject, Double> studentMarks,
                      @JsonProperty("teacherSubject") Subject teacherSubject,
                      @JsonProperty("teacherWorkTime") String teacherWorkTime) { }
}
