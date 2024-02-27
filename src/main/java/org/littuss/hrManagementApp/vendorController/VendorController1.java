//package org.littuss.hrManagementApp.vendorController;
//
//public class VendorController1 {
//
//}
package org.littuss.hrManagementApp.vendorController;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;
import java.util.Map;


import org.littuss.hrManagementApp.vendorDetailsModel.VendorDetails;
import org.littuss.hrManagementApp.vendorRepo.vendorRepo;
import org.littuss.hrManagementApp.vendorService.vendorService;


@RestController
@CrossOrigin("*")
@RequestMapping("/vendors")
public class VendorController1 {


       @Autowired
        private vendorService vendorService;
        
        @Autowired
        private vendorRepo repo;


//        // GET request to retrieve details by ID
//        @GetMapping("/{id}")
//        public ResponseEntity<?> getVendorById(@PathVariable Long id) {
//            try {
//                VendorDetails vendorDetails = vendorService.getDataById(id);
//                return ResponseEntity.ok(vendorDetails);
//            } catch (RuntimeException e) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Data not found");
//            }
//        }
//        
////        @PostMapping("/save/vendorName")
////        public VendorDetails addRequirement(@RequestBody VendorDetails VendorDetails) {
////            return venDet.save(VendorDetails);
////        }
//
//        // POST request to upload a file
//        @PostMapping(value = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE })
//       // @PostMapping("/upload")
//        public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("data") VendorDetails VendorDetails) {
//            try {
//                //String response =
//                vendorService.uploadFile(file);
//                vendorService.save(VendorDetails);
//                return ResponseEntity.ok("Data posted SucessFully"+VendorDetails.getId());
//            } catch (IOException e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
//            }
//        }
//
//        // PUT request to update vendor details by ID
//        @PutMapping("/{id}")
//        public ResponseEntity<VendorDetails> updateVendor(@PathVariable Long id, @RequestBody VendorDetails updatedVendorData) {
//            try {
//                VendorDetails updatedVendor = vendorService.updateUser(id, updatedVendorData);
//                return ResponseEntity.ok(updatedVendor);
//            } catch (EntityNotFoundException e) {
//                return ResponseEntity.notFound().build();
//            }
//        }
//
//        // PATCH request to update specific fields of vendor details by ID
///*        @PatchMapping("/{id}")
//        public ResponseEntity<VendorDetails> partialUpdateVendor(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
//            try {
//                VendorDetails updatedVendor = vendorService.partialUpdateUser(id, updates);
//                return ResponseEntity.ok(updatedVendor);
//            } catch (EntityNotFoundException e) {
//                return ResponseEntity.notFound().build();
//            }
//        }
//
//        // DELETE request to delete vendor details by ID
//        @DeleteMapping("/{id}")
//        public ResponseEntity<String> deleteVendor(@PathVariable Long id) {
//            String response = vendorService.deleteUser(id);
//            return ResponseEntity.ok(response);
//        }
//    }*/


       @PostMapping("/create")
        public ResponseEntity<String> saveUser(
               // @RequestParam String activeStatus,
                @RequestParam String vendorName,
                @RequestParam String rateCard,
                @RequestParam String location,
                @RequestParam String jobType,
                @RequestParam String payRoll,
                @RequestParam String candidateName,
                @RequestParam String candidateLocation,
                @RequestParam String technology,
                @RequestParam String firstReviewed,
                @RequestParam String secondReviewed,
                @RequestParam MultipartFile file) {
            


            createUser( vendorName, rateCard, location, jobType, payRoll, candidateName, candidateLocation, technology, firstReviewed, secondReviewed, file);


           return ResponseEntity.ok("User created successfully!");
        }
        
     // Endpoint to retrieve a user by email
        @GetMapping("/get/{id}")
        public ResponseEntity<VendorDetails> getUserByEmail(@PathVariable Long id) {
            VendorDetails vendor=  vendorService.getDataById(id);
            return (vendor != null) ? ResponseEntity.ok(vendor) : ResponseEntity.notFound().build();
        }


       // Endpoint to retrieve the uploaded file by user email
        @GetMapping("/file/{id}")
        public ResponseEntity<org.springframework.core.io.Resource> getFileByEmail(@PathVariable Long id, HttpServletResponse response) throws IOException {
            byte[] fileContent = vendorService.loadFileById(id);


           if (fileContent != null) {
                // Create a ByteArrayResource to represent the file content
                ByteArrayResource resource = new ByteArrayResource(fileContent);


               // Return the file content as a response with appropriate headers
                return ResponseEntity.ok()
                        .header("Content-Disposition", "attachment; filename=user_file.txt")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        }
        
        public  void createUser( String vendorName, String rateCard, String location, String jobType, String payRoll,
                String candidateName, String candidateLocation, String technology, String firstReviewed,
                String secondReviewed,  MultipartFile file) {
            try {
                byte[] fileContent = file.getBytes(); // Convert the uploaded file to byte array
                VendorDetails user = new VendorDetails( vendorName, rateCard, location, jobType, payRoll, candidateName, candidateLocation, technology, firstReviewed, secondReviewed, fileContent);
                        repo.save(user);
            } catch (IOException e) {
                // Handle the IOException (e.g., log the error, return an error response)
                e.printStackTrace();
            }
        }
}