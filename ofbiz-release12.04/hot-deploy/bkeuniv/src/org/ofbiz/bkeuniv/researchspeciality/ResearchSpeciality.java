package src.org.ofbiz.bkeuniv.researchspeciality;

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

public class ResearchSpeciality {
	public static Map<String, Object> getResearchSpeciality(DispatchContext ctx, Map<String, ? extends Object> context) {
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher localDispatcher = ctx.getDispatcher();
		
		String u1 = (String)ctx.getAttribute("userLoginId");
		String u2 = (String)context.get("userLoginId");
		
		if(u1 == null) u1 = "NULL";
		if(u2 == null) u2 = "NULL";
		
		String[] keys = {"researchSpecialityId", "researchSpecialityName", "researchDomainId"};
		String[] search = {"researchSpecialityId"};
		try {
			List<EntityCondition> conditions = new ArrayList<EntityCondition>();
			EntityFindOptions findOptions = new EntityFindOptions(true, EntityFindOptions.TYPE_SCROLL_INSENSITIVE, EntityFindOptions.CONCUR_READ_ONLY, true);
			for(String key: keys) {
				Object el = context.get(key);
				if(!(el == null||el==(""))) {
					EntityCondition condition;
					int index = Arrays.asList(search).indexOf(key);
					if( index == -1) {
						condition = EntityCondition.makeCondition(key, EntityOperator.EQUALS, el);
					} else {
						condition = EntityCondition.makeCondition(key, EntityOperator.LIKE, el);
					}
					conditions.add(condition);
				}
			}
			List<GenericValue> list = delegator.findList("ResearchSpeciality", EntityCondition.makeCondition(conditions), null, null, findOptions, false);
			Map<String, Object> result = ServiceUtil.returnSuccess();
			
			List<Map> listResearchSpeciality = FastList.newInstance();
			for(GenericValue el: list) {
				Map<String, Object> mapResearchSpeciality = FastMap.newInstance();
				mapResearchSpeciality.put("researchSpecialityId", el.getString("researchSpecialityId"));
				mapResearchSpeciality.put("researchSpecialityName", el.getString("researchSpecialityName"));
				mapResearchSpeciality.put("researchDomainId", el.getString("researchDomainId"));
				listResearchSpeciality.add(mapResearchSpeciality);
			}
			//result.put("result", listResearchSpeciality);
			result.put("researchSpeciality", listResearchSpeciality);
			return result;
		
		} catch (Exception e) {
			System.out.print("Research Speciality Error");
			Map<String, Object> rs = ServiceUtil.returnError(e.getMessage());
			return rs;
		}
	}
	
	public static Map<String, Object> createResearchSpeciality(DispatchContext ctx,
			Map<String, ? extends Object> context) {

		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatcher = ctx.getDispatcher();

		GenericValue userLogin = (GenericValue) context.get("userLogin");
		Locale locale = (Locale) context.get("locale");

		Map<String, Object> retSucc = ServiceUtil.returnSuccess();

		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String researchSpecialityName = (String) context.get("researchSpecialityName");
		List researchDomainId = (List) context.get("researchDomainId[]");
		
		GenericValue gv = delegator.makeValue("ResearchSpeciality");

		try {
			gv.put("researchSpecialityId", researchSpecialityId);
			gv.put("researchSpecialityName", researchSpecialityName);
			gv.put("researchDomainId", researchDomainId.get(0));
			
			delegator.create(gv);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ServiceUtil.returnError(ex.getMessage());
		}
		
		retSucc.put("researchSpeciality", gv);
		retSucc.put("message", "Create new row");
		return retSucc;
	}
	
	public static Map<String, Object> deleteResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
        Delegator delegator = ctx.getDelegator();
        LocalDispatcher dispatcher = ctx.getDispatcher();
        
        GenericValue userLogin = (GenericValue) context.get("userLogin");
        Locale locale = (Locale) context.get("locale");        
        
        Map<String,Object> retSucc = ServiceUtil.returnSuccess();
        
        String researchSpecialityId = (String)context.get("researchSpecialityId");
        try{
        	GenericValue gv = delegator.findOne("ResearchSpeciality", UtilMisc.toMap("researchSpecialityId",researchSpecialityId), false);
        	if(gv != null){
        		delegator.removeValue(gv);
        		retSucc.put("result", "deleted record with id: " + researchSpecialityId);
        	} else {
        		retSucc.put("result", "not found record with id: " + researchSpecialityId);
        	}
        	
        }catch(Exception ex){
        	ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        }
        return retSucc;
	}
	
	public static Map<String, Object> updateResearchSpeciality(DispatchContext ctx, 
			Map<String, ? extends Object> context) {
		Map<String,Object> retSucc = ServiceUtil.returnSuccess();
		
		Delegator delegator = ctx.getDelegator();
		LocalDispatcher dispatch = ctx.getDispatcher();
		
		String researchSpecialityId = (String) context.get("researchSpecialityId");
		String researchSpecialityName = (String) context.get("researchSpecialityName");
		List researchDomainId = (List) context.get("researchDomainId[]");
		
		try{
			GenericValue gv = delegator.findOne("ResearchSpeciality", false, UtilMisc.toMap("researchSpecialityId",researchSpecialityId));
			if(gv != null){
				
				gv.put("researchSpecialityId", researchSpecialityId);
				gv.put("researchSpecialityName", researchSpecialityName);
				gv.put("researchDomainId", researchDomainId.get(0));
				
				delegator.store(gv);
				
				Map<String, Object> rs = new HashMap<String, Object>();
				rs.put("researchSpecialityId", researchSpecialityId);
				rs.put("researchSpecialityName", researchSpecialityName);
				rs.put("researchDomainId", researchDomainId);
				
				retSucc.put("researchSpeciality", rs);
        		retSucc.put("message", "updated record with id: " + researchSpecialityId);
        	} else {
        		retSucc.put("message", "not found record with id: " + researchSpecialityId);
        	}
			
		}catch(Exception ex){
			ex.printStackTrace();
        	return ServiceUtil.returnError(ex.getMessage());
        
		}
		return retSucc;
	}
}