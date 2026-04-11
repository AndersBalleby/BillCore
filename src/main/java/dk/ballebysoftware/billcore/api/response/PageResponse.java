package dk.ballebysoftware.billcore.api.response;

import java.util.List;


public class PageResponse<T> {
  
  private List<T> data;
  private Meta meta;

  public static class Meta {
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
  }
}
