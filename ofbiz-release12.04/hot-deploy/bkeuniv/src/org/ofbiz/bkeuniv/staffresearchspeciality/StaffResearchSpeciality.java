package src.org.ofbiz.bkeuniv.staffresearchspeciality;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.ofbiz.base.util.Debug;
import org.ofbiz.base.util.UtilHttp;
import org.ofbiz.base.util.UtilMisc;
import org.ofbiz.entity.Delegator;
import org.ofbiz.entity.GenericValue;
import org.ofbiz.entity.condition.EntityCondition;
import org.ofbiz.entity.condition.EntityOperator;
import org.ofbiz.entity.util.EntityFindOptions;
import org.ofbiz.service.DispatchContext;
import org.ofbiz.service.LocalDispatcher;
import org.ofbiz.service.ServiceUtil;

import java.sql.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javolution.util.FastList;
import javolution.util.FastMap;
import src.org.ofbiz.utils.BKEunivUtils;

public class StaffResearchSpeciality {
	public final static String module = StaffResearchSpeciality.class.getName();
	
	public static String createStaffResearchSpecialityRequestResponse(HttpServletRequest request, HttpServletResponse response){
		LocalDispatcher dispatcher = (LocalDispatcher) request.getAttribute("dispatcher");
		Delegator delegator = (Delegator) request.getAttribute("delegator");
		Locale locale = UtilHttp.getLocale(request);
		GenericValue userLogin = (GenericValue) request.getSession().getAttribute("userLogin");
		GenericValue staff = (GenericValue)request.getSession().getAttribute("staff");
		System.out.println("StaffResearchSpeciality::createStaffResearchSpecialityRequestResponse, Staff = " + staff.get("staffEmail"));
		
		Map<String, Object> context = FastMap.newInstance();
		context.put("staffResearchSpecialityId",request.getParameter("staffResearchSpecialityId"));
		context.put("staffId",staff.get("staffId"));
		context.put("researchSpecialityId",request.getParameter("researchSpecialityId"));
		context.put("fromDate",request.getParameter("fromDate"));
		context.put("thruDate",request.getParameter("thruDate"));
		try{
			Map<String, Object> resultNewStaffResearchSpeciality = dispatcher.runSync("createStaffResearchSpeciality", context);
			BKEunivUtils.writeJSONtoResponse(BKEunivUtils.parseJSONObject(resultNewStaffResearchSpeciality), response, 200);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "success";
	}
	
	public static Map<String, Object> getStaffResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String staffResearchSpecialityId = (String)context.get("staffResearchSpecialityId");
		String staffId = (String)context.get("staffId");
		String researchSpecialityId = (String)context.get("researchSpecialityId");		
		String fromDate = (String)context.get("fromDate");	
		String thruDate = (String)context.get("thruDate");	
		
		System.out.println(staffResearchSpecialityId);
		System.out.println(staffId);
		System.out.println(researchSpecialityId);
		System.out.println(fromDate);
		System.out.println(thruDate);
		
		try {
			 Map<String, Object> result = ServiceUtil.returnSuccess();
			 EntityCondition entity;
			 
			 EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			 List<GenericValue> list;
			 if(staffResearchSpecialityId == null) {
					list = delegator.findList("StaffResearchSpeciality", null, null, null, findOptions, true);					
			 }
			  else {				
				  entity = EntityCondition.makeCondition("staffResearchSpecialityId", EntityOperator.EQUALS, staffResearchSpecialityId);				
				  list = delegator.findList("StaffResearchSpeciality", entity, null, null, findOptions, true);	
			}
			 result.put("staffResearchSpeciality", list);
			 return result;						 
			 
			 
	
		} catch (Exception e) {
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
  			return rs; 			
  		}				
	}
	
	public static Map<String, Object> createStaffResearchSpeciality(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();
		
		//String staffResearchSpecialityId = (String) context.get("staffResearchSpecialityId");
		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String staffId = (String) context.get("staffId");
		String fromDate = (String) context.get("fromDate");
		String thruDate = (String) context.get("thruDate");
		
		//System.out.println(staffResearchSpecialityId);
		System.out.println(staffId);
		System.out.println(researchSpecialityId);
		System.out.println(fromDate);
		System.out.println(thruDate);
		
		System.out.println("StaffResearchSpeciality::createStaffResearchSpeciality, staffId = " + staffId + 
				", researchSpecialityId = " + researchSpecialityId + ", fromDate = " + fromDate + ", thruDate = " + thruDate);
		
		GenericValue gv = delegator.makeValue("StaffResearchSpeciality");
		gv.put("staffResearchSpecialityId", delegator.getNextSeqId("StaffResearchSpeciality"));

		try {
			gv.put("researchSpecialityId", researchSpecialityId);
			gv.put("staffId", staffId);
			gv.put("fromDate", Date.valueOf(fromDate));
			gv.put("thruDate", Date.valueOf(thruDate));
			
			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("staffResearchSpeciality", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
	}
	
	public static Map<String, Object> deleteStaffResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String staffResearchSpecialityId = (String)context.get("staffResearchSpecialityId");
        try{
        	GenericValue gv = delegator.findOne("StaffResearchSpeciality", UtilMisc.toMap("staffResearchSpecialityId",staffResearchSpecialityId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + staffResearchSpecialityId);
        	} else {
        		retSucc.put("result", "not found record with id: " + staffResearchSpecialityId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateStaffResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String staffResearchSpecialityId = (String) context.get("staffResearchSpecialityId");
		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String staffId = (String) context.get("staffId");
		String fromDate = (String) context.get("fromDate");
		String thruDate = (String) context.get("thruDate");
		
		try{
			GenericValue gv = delegator.findOne("StaffResearchSpeciality", false, UtilMisc.toMap("staffResearchSpecialityId",staffResearchSpecialityId));
			if(gv != null){
				
				
				gv.put("researchSpecialityId", researchSpecialityId);
				gv.put("staffId", staffId);
				gv.put("fromDate", Date.valueOf(fromDate));
				gv.put("thruDate", Date.valueOf(thruDate));
				
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("staffResearchSpecialityId", staffResearchSpecialityId);
				rs.put("researchSpecialityId", researchSpecialityId);		
				rs.put("staffId", staffId);
				rs.put("fromDate", fromDate);
				rs.put("thruDate", thruDate);
				
				retSucc.put("staffResearchSpeciality", rs);
        		retSucc.put("message", "updated record with id: " + staffResearchSpecialityId);
        	} else {
        		retSucc.put("message", "not found record with id: " + staffResearchSpecialityId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}