package com.miempresa.controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.miempresa.interfaceServicio.IEmpleadoServicio;
import com.miempresa.interfaceServicio.ITareaServicio;
import com.miempresa.interfaces.Iempleado;
import com.miempresa.interfaces.Itarea;
import com.miempresa.modelo.Empleado;
import com.miempresa.modelo.Tarea;

@Controller
@RequestMapping
public class controlador {
	
	@Autowired
	private IEmpleadoServicio servicio;
	
	@GetMapping("/listarEmpleados")
	private String listarEmpleados(Model model) {
		List<Empleado> empleados = servicio.listar();
		model.addAttribute("empleados", empleados);
		return "empleados";
	}
	@Autowired
    private Iempleado empleadoRepository;
	@GetMapping("/buscar-empleado")
	private String buscarEmpleado(@RequestParam("nombre") String nombre, @RequestParam("seccion") String seccion, Model model) {
	    if (seccion.equals("empleados")) {
	        List<Empleado> empleados = empleadoRepository.findByNombreContainingIgnoreCase(nombre);
	        model.addAttribute("empleados", empleados);
	    }
		List<Empleado> empleados = empleadoRepository.findByNombreContainingIgnoreCase(nombre);
        model.addAttribute("empleados", empleados);
        return "buscarEmpleado";
    }
	@GetMapping("/agregarEmpleado")
	public String agregarEmpleado(Model model) {
		model.addAttribute("empleado",new Empleado());
		return "agregarEmpleado";
	}
	@PostMapping("/guardarEmpleado")
	public String guardarEmpleado(Empleado p) {
		servicio.guardar(p);
		return "redirect:/listarEmpleados";
	}
	@GetMapping("editarEmpleado/{id}")
	public String editarEmpleado(@PathVariable int id, RedirectAttributes atributos) {
		Optional<Empleado> empleado = servicio.listarId(id);
		atributos.addFlashAttribute("empleado",empleado);
		return "redirect:/mostrarEmpleado";
	}
	@GetMapping("/eliminarEmpleado/{id}")
	public String eliminarEmpleado(@PathVariable int id) {
		servicio.borrar(id);
		return "redirect:/listarEmpleados";
	}
	
	@GetMapping("/mostrarEmpleado")
	public String mostrarEmpleado(@ModelAttribute("empleado") Empleado p, Model model) {
		model.addAttribute("empleado", p);
		return "agregarEmpleado";
	}
	
	@Autowired
	private ITareaServicio servicios;
	
	@GetMapping("/listarTareas")
	private String listarTareas(Model model) {
		List<Tarea> tareas = servicios.listar();
		model.addAttribute("tareas", tareas);
		return "tareas";
	}
}
