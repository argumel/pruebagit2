package mx.com.televisa.cfdis.service.sendptt;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"response"})
@XmlRootElement(name = "Responses")
public class Responses {
  @XmlElement(name = "Response", required = true)
  protected List<Response> response;
  
  public List<Response> getResponse() {
    return this.response;
  }
  
  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "", propOrder = {"error", "descripcion"})
  public static class Response {
    @XmlElement(name = "Error")
    protected List<Integer> error;
    
    @XmlElement(name = "Descripcion", required = true)
    protected List<String> descripcion;
    
    public List<Integer> getError() {
      return this.error;
    }
    
    public void setError(List<Integer> error) {
      this.error = error;
    }
    
    public List<String> getDescripcion() {
      return this.descripcion;
    }
    
    public void setDescripcion(List<String> descripcion) {
      this.descripcion = descripcion;
    }
  }
}
