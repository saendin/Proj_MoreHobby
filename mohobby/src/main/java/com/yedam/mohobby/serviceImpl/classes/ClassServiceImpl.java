package com.yedam.mohobby.serviceImpl.classes;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.yedam.mohobby.mapper.classes.ClassMapper;
import com.yedam.mohobby.service.classes.ClassAttendanceVO;
import com.yedam.mohobby.service.classes.ClassBoardVO;
import com.yedam.mohobby.service.classes.ClassChapterVO;
import com.yedam.mohobby.service.classes.ClassCurriculumVO;
import com.yedam.mohobby.service.classes.ClassImageVO;
import com.yedam.mohobby.service.classes.ClassInfoRequestVO;
import com.yedam.mohobby.service.classes.ClassListRequestVO;
import com.yedam.mohobby.service.classes.ClassNeedsVO;
import com.yedam.mohobby.service.classes.ClassPayVO;
import com.yedam.mohobby.service.classes.ClassReviewVO;
import com.yedam.mohobby.service.classes.ClassService;
import com.yedam.mohobby.service.classes.ClassesVO;
import com.yedam.mohobby.service.communal.JjimVO;
import com.yedam.mohobby.web.classes.ClassController;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    ClassMapper classMapper;

    // 강의개설
    @Override
    public void insertClass(ClassesVO classes) {
        classMapper.insertClass(classes);
    }

    // 목록조회
    @Override
    public List<ClassesVO> listAll(ClassListRequestVO req) {
        return classMapper.listAll(req);
    }
    
    // 모집중인 강의 조회
    @Override
    public List<ClassesVO> listOnClass() {
    	return classMapper.listOnClass();
    }

    // 단건조회
    @Override
    public ClassesVO listOne(ClassListRequestVO req) {
        return classMapper.listOne(req);
    }
    
    // 후기평가조회
    @Override
    public ClassReviewVO getClassReview(int classId) {
    	return classMapper.getClassReview(classId);
    }
    
    // 강의게시글조회
    @Override
    public List<ClassBoardVO> getClassBoardList(int classId, int boardType, String memberId) {
    	return classMapper.getClassBoardList(classId, boardType, memberId);
    }
    
    // 강의게시글등록
    @Override
    public ClassBoardVO addClassBoard(ClassBoardVO board) {
        classMapper.addClassBoard(board);
        return board;
    }
    
    // 강의게시글수정
    @Override
    public void updateClassBoard(ClassBoardVO board) {
        classMapper.updateClassBoard(board);
    }

    // 강의게시글삭제
    public void deleteClassBoard(int boardId) {
        classMapper.deleteClassBoard(boardId);
    }
    
    // 찜등록
    @Override
    public void addJjim(JjimVO jjim) {
        classMapper.addJjim(jjim);
    }

    // 찜삭제
    @Override
    public void deleteJjim(JjimVO jjim) {
        classMapper.deleteJjim(jjim);
    }
    
    // 강의챕터조회
    @Override
    public List<ClassChapterVO> getChapterList(int classId, String memberId) {
    	ClassChapterVO vo = new ClassChapterVO();
    	vo.setMemberId(memberId);
    	vo.setClassId(classId);
        return classMapper.getChapterList(vo);
    }
    
    // 강의 커리큘럼 진행율 조회
    @Override
    public ClassAttendanceVO getCurrProgress(ClassAttendanceVO vo) {
    	return classMapper.getCurrProgress(vo);
    }
    
    // 강의 커리큘럼 단건 조회
    @Override
    public ClassCurriculumVO getCurrInfo(int currId, String memberId) {
    	return classMapper.getCurrInfo(currId, memberId);
    }
    
    // 강의 커리큘럼 단건 업데이트
    @Override
    public void updateAttdInfo(ClassAttendanceVO vo) {
    	classMapper.updateAttdInfo(vo);
    }
    
    //강의 결제내역 추가 (준비물 배송 정보도 추가)
    @Override
    public int insertClassPayOne(ClassPayVO vo) {
    	classMapper.insertClassPayOne(vo);
    	int payId = vo.getPayId();
    	
    	return payId;
    }
    
    // 강의 결제내역 단건조회
    @Override
    public ClassPayVO getClassPayOne(ClassPayVO vo) {
    	return classMapper.getClassPayOne(vo);
    }
    
    //강의 준비물 조회
    @Override
    public List<ClassNeedsVO> getClassNeedsInfo(int classId) {
    	return classMapper.getClassNeedsInfo(classId);
    }
    
    //내 강의 목록 조회
    @Override
    public List<ClassesVO> getMyCourse(String memberId, int classStatus) {
    	ClassPayVO reqBody = new ClassPayVO();
    	reqBody.setMemberId(memberId);
    	reqBody.setClassStatus(classStatus);
    	
    	List<ClassesVO> resp = classMapper.getMyCourse(reqBody);
    	
    	for(int i = 0; i < resp.size(); i++) {
    		ClassesVO vo = resp.get(i);
    		HashMap<String, Integer> result = classMapper.getMyCourseProgress(vo.getClassId(), memberId);
    		
    		int view = Integer.parseInt(String.valueOf(result.get("TOTAL_VIEW_PROGRESS")));
    		int curr = Integer.parseInt(String.valueOf(result.get("LAST_CURR_ID")));
    		
    		resp.get(i).setTotalViewProgress(view);
    		resp.get(i).setLastCurrId(curr);
    	}
    	
    	System.out.println("rest");
    	System.out.println(resp);
    	
    	return resp;
    }
    
    //내 수료증 발급 가능한 강의 목록 조회
    public List<ClassesVO> getMyCourseCertificate(String memberId) {
    	return classMapper.getMyCourseCertificate(memberId);
    }
    
    
    // 강의 개설 신청
    @Override
    public void openClassForm(
			ClassesVO classVO, 
			MultipartFile mainImage, 
			List<MultipartFile> subImageList, 
			ClassInfoRequestVO contentVO,
			List<ClassChapterVO> chapList, 
			List<ClassCurriculumVO> currList,
			List<MultipartFile> videoList
			) {
    	//강의 정보 인서트
    	classMapper.insertClass(classVO);
    	
    	int classId = classVO.getClassId();
    	
    	//강의 소개 html 저장
    	contentVO.setFilename(String.valueOf(classId));
    	saveClassInfo(contentVO);
    	
    	//강의 썸네일 이미지 저장
    	saveThumbnail(classId, mainImage, subImageList);
    	
    	//강의 챕터 생성
    	HashMap<String, Integer> chapIdList = new HashMap<>();
    	for(ClassChapterVO chap : chapList) {
    		chap.setClassId(classId);
    		classMapper.insertChaper(chap);
    		chapIdList.put(chap.getChapName(), chap.getChapId());
    	}
    	
    	//강의 커리큘럼 생성
    	List<Integer> currIdList = new ArrayList<>();
    	for(ClassCurriculumVO curr : currList) {
    		int chapId = chapIdList.get(curr.getChapName());
    		curr.setChapId(chapId);
    		curr.setClassId(classId);
    		classMapper.insertCurriculum(curr);
    		currIdList.add(curr.getCurrId());
    	}
    	
    	//비디오 저장
    	saveVideos(videoList, currIdList);
    	
    	
    }
    
    // 강의 영상 저장
    @Transactional
    @Override
    public void saveVideos(List<MultipartFile> videoList, List<Integer> currIdList) {
    	String path = ClassController.class.getResource("/").getPath();
        path = path.substring(0, path.lastIndexOf("mohobby"));
        path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

        path += "/mohobby/mohobby/src/assets/video/class/curriculum";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        System.out.println(videoList);
        
    	
    	for(int i=0; i<videoList.size(); i++) {
    		try {
	    		MultipartFile video = videoList.get(i);
	    		String savePath = path + "/" + currIdList.get(i) + ".mp4";
	    		
	    		File save = new File(savePath);
				video.transferTo(save);
	    		
	    		
    		} catch (IllegalStateException | IOException e) {
    			e.printStackTrace();
    		}
    	}
    }
    
    // 강의 썸네일 이미지 저장
    @Transactional
    @Override
    public void saveThumbnail(int classId, MultipartFile mainImage, List<MultipartFile> subImageList) {
        String path = ClassController.class.getResource("/").getPath();
        path = path.substring(0, path.lastIndexOf("mohobby"));
        path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

        path += "/mohobby/mohobby/src/assets/image/class/thumb/";
        path += String.valueOf(classId);
        
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        try {
        	
        	File main = new File(path + "/0.jpg");
			mainImage.transferTo(main);
			
			if(subImageList.size() == 0) return;
			
			for(int i=0; i<subImageList.size(); i++) {
				MultipartFile sub = subImageList.get(i);
				
				File save = new File(path + "/" + (i+1) + ".jpg");
				sub.transferTo(save);
			}
			
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}
        
    }

    // html 저장
    @Override
    public void saveClassInfo(ClassInfoRequestVO req) {
        String path = ClassController.class.getResource("/").getPath();
        path = path.substring(0, path.lastIndexOf("mohobby"));
        path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

        path += "/mohobby/mohobby/src/assets/html/classInfo/";
        path += req.getFilename();
        path += ".html";
        System.out.println(path);

        File file = new File(path);

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(req.getContent().getBytes());
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // html 읽기
    @Override
    public String readClassInfo(int classId) {
        String path = ClassController.class.getResource("/").getPath();
        path = path.substring(0, path.lastIndexOf("mohobby"));
        path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

        path += "/mohobby/mohobby/src/assets/html/classInfo/";
        path += String.valueOf(classId);
        path += ".html";
        
        File file = new File(path);
        String res = "";
        
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            
            String str;
            
            while ((str = br.readLine()) != null) {
                res += str;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        return res;
    }

    //출결 관리 정보 가져오기
    @Override
    public ClassAttendanceVO getAttendanceInfo(String memberId) {
    	ClassAttendanceVO info = new ClassAttendanceVO();
    	
    	info = classMapper.getTodayAttendanceInfo(memberId);
    	
    	if(info == null) info = classMapper.getNoneClassInfo(memberId);
    	
    	ClassAttendanceVO calc = classMapper.getTodayAttendanceCalc(memberId, info.getClassId());
    	ClassAttendanceVO progress = classMapper.getTodayClassProgress(info.getClassId());
    	
    	info.setSumAttd(calc.getSumAttd());
    	info.setSumOut(calc.getSumOut());
    	info.setSumLeave(calc.getSumLeave());
    	info.setSumAbsent(calc.getSumAbsent());
    	info.setSumLate(calc.getSumLate());
    	info.setTotal(progress.getTotal());
    	info.setGone(progress.getGone());
    	info.setProgress(progress.getProgress());
    	
    	return info;
    }

    //출결 정보 입력
    @Override
    public void insertAttendanceInfo(HashMap<String, String> req) {
    	int type = Integer.parseInt(req.get("type"));
    	if(type==0 || type==3) classMapper.insertAttendanceInfo(req);
    	else classMapper.updateAttendanceInfo(req);
    }
    		
    // qr코드 생성
    @Override
    public String createCodeImg(String link) {
        int width = 400;
        int height = 400;
        String base64 = "";
        try {
            BitMatrix matrix = new MultiFormatWriter().encode(link, BarcodeFormat.QR_CODE, width, height);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {

                MatrixToImageWriter.writeToStream(matrix, "PNG", out);

                base64 = new String(Base64.getEncoder().encode(out.toByteArray()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return "data:image/png;base64," + base64;
    }

    
    // 에디터 이미지 저장
    @Override
    public void uploadClassImage(ClassImageVO vo) {
    	System.out.println(vo);
        File file = null;
        
        String path = ClassController.class.getResource("/").getPath();
        path = path.substring(0, path.lastIndexOf("mohobby"));
        path = path.substring(0, path.lastIndexOf("mohobby") + "mohobby".length());

        path += "/mohobby/mohobby/src/assets/image/class/info/";
        path += String.valueOf(vo.getFoldername());
        //path += "/";
        
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        
        byte[] targetBytes = null;
        FileOutputStream fos = null;
        
        if(vo.getSrc().contains("base64")) {
        	System.out.println(vo.getSrc().split(",")[1]);
        	String data = vo.getSrc().split(",")[1];
        	targetBytes = data.getBytes();
        	byte[] bytes = Base64.getDecoder().decode(targetBytes);
        	
            try {
            	file = new File(path + "/" + vo.getFilename() + ".jpg");
            	fos = new FileOutputStream(file);
            	fos.write(bytes);
            	fos.close();
            } catch(Exception e) {
            	e.printStackTrace();
            } finally {
            	if (fos != null) {
            		try {
            			fos.close();
            		} catch (IOException e) {
            			e.printStackTrace();
            		}
            	}
            }
        } else {
        	System.out.println("기존파일");
        }
        
        
    }
    
    


}
