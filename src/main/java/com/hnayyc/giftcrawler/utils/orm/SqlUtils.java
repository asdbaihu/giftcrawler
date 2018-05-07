package com.hnayyc.giftcrawler.utils.orm;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dl.udp.hr.model.Admindept;
import com.dl.utils.PropertyColumnTransfer;
/**
 * 组装SQL
 * @author wp
 *
 */
public class SqlUtils {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 根据对象创建SQL
	 * @author wp
	 *
	 */
	public static Map<String,Object> createSqlWhere(Object obj,String alias ,String [] properties,String [] operators,String[] relations) throws Exception {
		// TODO Auto-generated method stub
		String column =null;
		Class clazz=obj.getClass();
		Map<String,Object> result=new HashMap();
		List<Object> values=new ArrayList<Object>();
		String where ="";
		int i=0; 
		String preCondition="";
		String currrentCondition="";
		String relation="";
		for(String property : properties){
			column=PropertyColumnTransfer.propertyToField(property);
			Object value=BeanUtils.getProperty(obj, property);	
			if(value!=null){
				currrentCondition=createSqlSingleWhere(alias,column,i<operators.length?operators[i]:"=");
				relation=relations!=null?((i>0&&(i-1)<relations.length)?relations[i-1]:" and "):" and ";
				if(!preCondition.equals("")){
					where=where+relation+currrentCondition;
				}else{
					where=where+currrentCondition;
				}
				if("like".equals(operators[i])){
					values.add("%"+value+"%");
				}else{
					values.add(value);
				}
				preCondition=currrentCondition;
			}
			
			i++;
		}
		if(!where.equals("")){
			result.put("where",where);
			result.put("values", values.toArray());
		}
		
		return result;
	}
	
	/**
	 * 创建where条件
	 * @param alias
	 * @param column
	 * @param operator
	 * @return
	 */

	public static String createSqlSingleWhere(String alias,String column,String  operator) {
		return " ("+alias+"."+column+" "+operator+" ?) ";
	}
	
	/**
	 * 创建where条件
	 * @param obj
	 * @param alias
	 * @param properties
	 * @param operators
	 * @param relations
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> createSqlWhere(Object obj,String alias ,String  properties,String  operators,String relations) throws Exception {
		return SqlUtils.createSqlWhere(obj, alias, properties.split(","), operators.split(","), relations.split(","));
	}
	/**
	 * 创建MYSQL的分页语句
	 * @param page
	 * @return
	 */
	public static String createLimitSql(Page page) {
		if(page.getLimit()>0){
			return " LIMIT "+page.getFirst()+","+page.getLimit();
		}else{
			return "";
		}
		
	}
	/**
	 * 创建where条件
	 * @param column
	 * @param operator
	 * @return
	 */
	public static String createSqlSingleWhere2(String column,String  operator) {
		return " (? = 'NULL' OR ? IS NULL OR ? = '' OR "+column+" "+operator+"  ?)";
	}
	/**
	 * 创建where条件
	 * @param column
	 * @param operator
	 * @param value
	 * @param oldValue
	 * @param list
	 * @return
	 */
	public static String createSqlSingleWhere3(String column,String  operator, Object value,Object oldValue,List list) {
		String sqlWhere = "";
		if(oldValue !=null&&!oldValue.equals("")){
			sqlWhere=sqlWhere+" and "+column+" "+operator+" ? ";
				list.add(value);		
		}
		return sqlWhere;
	
	}
	/**
	 *  创建where条件
	 * @param columns
	 * @param operators
	 * @param values
	 * @param oldvalues
	 * @param list
	 * @return
	 */
	public static String createSqlSingleWhere4(String[]  columns,String[]   operators, Object[] values,Object[] oldvalues,List list) {
		String sqlWhere = "";
		for(int i=0;i<values.length;i++){	
			sqlWhere=sqlWhere+createSqlSingleWhere3(columns[i],operators[i],values[i],oldvalues[i],list);	
		}	
		if(!sqlWhere.equals("")){
			sqlWhere="where "+sqlWhere;
			sqlWhere = sqlWhere.replaceFirst("and", "");
		}
		return sqlWhere;
	}
	
	
	/**
	 * 创建In语句
	 * @param length
	 * @return
	 */
	public static Object createIns(Integer length) {
		String  ins="";
		for(int i=0;i<length;i++){
			ins=",?"+ins;
		}
		if(ins.length()>0){
			ins=ins.replaceFirst(",", "");
		}
		return  ins;
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.print(SqlUtils.createSqlSingleWhere("dept","name", "="));
		Admindept admindept=new Admindept();
		admindept.setParentId("parentId");
		try {
//			System.out.print(SqlUtils.createSqlWhere(admindept, "dept",new String[]{"name","adminorgId","parentId"}, new String[]{"=","=","="}, new String[]{"and"}).get("where"));
			System.out.print(UUID.randomUUID());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
