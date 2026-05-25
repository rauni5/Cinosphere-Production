<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8" isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>About Us | CinoSphere</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/aboutus.css">
</head>

<body>

    <jsp:include page="../components/header.jsp" />
    
    <main class="about_screen"> 
    
    	<section class="about_hero_section">
	    	<div class="about_hero_overlay"></div>
			    <div class="about_hero_container">
			        <div class="about_hero_text_content">
			            <h1 class="about_hero_primary_title">
			                The <em>Vision</em><br>
			                Behind <em>the Screen</em>
			            </h1>
			            <p class="about_hero_subtitle">
			                Born from a vision of world class cinema, experience entertainment beyond the ordinary.
			            </p>
			            <div class="about_hero_action_group">
			                <a href="${pageContext.request.contextPath}/movies" class="about_hero_button about_hero_button_primary">Explore Movies</a>
			               <a href="#story" class="about_hero_button about_hero_button_secondary"> Our Story</a>
			            </div>
			        </div>
			   	</div>
		</section>
		
		<section class="about_statistics_section">
			<div class="accent_line top"></div>
		    <div class="about_statistics_header">
		        <span class="about_section_eyebrow"> Our Numbers </span>
		        <h2 class="about_section_title"> The scale of <em>ambition</em> </h2>
		    </div>
		
		    <div class="about_statistics_grid">
		        <div class="about_statistics_card">
		            <span class="about_statistics_value">18,000</span>
		            <span class="about_statistics_label"> Square Feet</span>
		            <span class="about_statistics_description">The largest screen surface within South Asia.</span>
		        </div>
		        
		        <div class="about_statistics_card">
		            <span class="about_statistics_value">44K</span>
		            <span class="about_statistics_label"> Watts of Audio</span>
		            <span class="about_statistics_description">12 channels IMAX certified speaker array installed throughout the halls.</span>
		        </div>
		        
		         <div class="about_statistics_card">
		            <span class="about_statistics_value">1400+</span>
		            <span class="about_statistics_label">Seats Across Halls</span>
		            <span class="about_statistics_description">Every seat made for comfort with identical sight lines and acoustic balance</span>
		        </div>
		        
		         <div class="about_statistics_card">
		            <span class="about_statistics_value">3</span>
		            <span class="about_statistics_label">Cities by 2027</span>
		            <span class="about_statistics_description">Kathmandu · Pokhara · Butwal</span>
		        </div>
		        <div class="accent_line bottom"></div>
			</div>
		</section>
		
		<section class="about_story_section" id="story">

		    <div class="about_story_grid">
		        <div class="about_story_content">
		            <span class="about_section_eyebrow"> The Journey</span>
		            <h2 class="about_section_title"> From <em>an idea</em> to<br>reality</h2>
		            <p class="about_story_description"> It all started with a conversation in a New York, after an IMAX screening of Spiderman NWH. We were blown away but with and a question, why can't we have this in Kathmandu?</p>
		
		            <p class="about_story_description about_story_description_spacing"> Two years later, that question became a company. Then a construction site. Then a screen. Then a world class experience.</p>
		
		            <div class="about_story_statistics">
		                <div class="about_story_statistic_item">
		                    <span class="about_story_statistic_value">2.4B+</span>
		                    <div class="about_story_statistic_label"> TOTAL INVESTMENT </div>
		                </div>
		                <div class="about_story_statistic_item">
		                    <span class="about_story_statistic_value">500+
		                    </span>
		                    <div class="about_story_statistic_label"> TEAM MEMBERS</div>
		                </div>
		                 <div class="about_story_statistic_item">
		                    <span class="about_story_statistic_value">3+
		                    </span>
		                    <div class="about_story_statistic_label"> OPERATIONAL VENUES</div>
		                </div>
		            </div>
		
		        </div>
		        <div class="about_story_timeline">
		            <div class="about_story_timeline_item">
		                <span class="about_story_timeline_year">
		                    2023: The Spark
		                </span>
		                <div class="about_story_timeline_title">
		                    The Idea 
		                </div>
		                <p class="about_story_timeline_description"> Founder Aditya Raut and co founder Raunit Giri watch Spiderman NWH in IMAX in NY. On the flight back to Kathmandu, they begin sketching what Nepal's first IMAX could look like.</p>
		
		            </div>
		
		            <div class="about_story_timeline_item">
		                <span class="about_story_timeline_year">
		                    The CinoSphere Regime
		                </span>
		                <div class="about_story_timeline_title">
		                    CinoSphere Nepal Pvt. Ltd. Incorporated
		                </div>
		                <p class="about_story_timeline_description">
		                    The company is registered. The core leadership team is assembled from cinema operations in Nepal, Singapore, and the UK. The IMAX Corporation partnership agreement is signed after 8 months of negotiation.
		                </p>
		            </div>
		
		            <div class="about_story_timeline_item">
		                <span class="about_story_timeline_year">
		                    Early 2025: Build
		                </span>
		                <div class="about_story_timeline_title">
		                    Construction Begins in Kathmandu
		                </div>
		                <p class="about_story_timeline_description">
		                    Ground breaks at Labim Mall, Lalitpur. The auditorium is engineered to IMAX specifications. A team of 140 construction workers and cinema specialists begin the 14 month project.
		                </p>
		
		            </div>
		
		            <div class="about_story_timeline_item">
		                <span class="about_story_timeline_year">
		                    Late 2025: Calibration
		                </span>
		                <div class="about_story_timeline_title">
		                    Laser System Commissioned
		                </div>
		                <p class="about_story_timeline_description">
		                    The IMAX dual laser 4K projection system is installed and calibrated over 6 weeks. IMAX Corporation's technical engineers fly in from Los Angeles for final certification. Nepal's first certified IMAX screen passes all benchmarks.
		                </p>
		            </div>
		            
		            <div class="about_story_timeline_item">
		                <span class="about_story_timeline_year">
		                    2026: A Grand Opening
		                </span>
		                <div class="about_story_timeline_title">
		                    CinoSphere Kathmandu Opens Its Doors
		                </div>
		                <p class="about_story_timeline_description">
		                    The first public screening, a private premiere for 350 guests takes place. Within the first week, 12,000 tickets are sold. The Sphere membership programme launches, reaching 5,000 members in 14 days.
		                </p>
		            </div>
		
		            <div class="about_story_timeline_item">
		                <span class="about_story_timeline_year">
		                    The Expansion Story
		                </span>
		                <div class="about_story_timeline_title">
		                    Pokhara & Butwal
		                </div>
		                <p class="about_story_timeline_description">
		                    Two additional locations are under construction or in final planning. Each location is purpose built, not converted. CinoSphere becomes Nepal's first national cinema chain with IMAX certified screens in all three halls.
		                </p>
		            </div>
		            
		        </div>
		    </div>
		
		</section>
		
		<section class="leadership_team_section">
			<div class="accent_line top"></div>
		    <div class="leadership_team_header">
		        <span class="about_section_eyebrow">Our Team</span>
		        <h2 class="about_section_title">Foundational Pillars of <em>CinoSphere</em> </h2>
		    </div>
		
		    <div class="leadership_team_grid">
		        <div class="leadership_team_card">
		            <div class="leadership_team_avatar">
		                <img src="${pageContext.request.contextPath}/profileimage?name=aditya" alt="Aditya">
		            </div>
		            <div class="leadership_team_name">Aditya Raut</div>
		            <span class="leadership_team_role">Full Stack Developer</span>
		            <p class="leadership_team_bio">CinosSphere Database, UI/UX, frontend and technical documentation engineer</p>
		        </div>
		
		        <div class="leadership_team_card">
		            <div class="leadership_team_avatar">
		                <img src="${pageContext.request.contextPath}/profileimage?name=raunit" alt="Raunit">
		            </div>
		            <div class="leadership_team_name">Raunit Giri</div>
		            <span class="leadership_team_role">Backend Developer</span>
		            <p class="leadership_team_bio">CinoSphere Architect turned core backend developer</p>
		        </div>
		
		        <div class="leadership_team_card">
		            <div class="leadership_team_avatar">
		                <img src="${pageContext.request.contextPath}/profileimage?name=sabal" alt="Sabal">
		            </div>
		            <div class="leadership_team_name">Sabal Sharma</div>
		            <span class="leadership_team_role">Technical Officer & QA</span>
		            <p class="leadership_team_bio">Documentation engineer with overseeing QA testing</p>
		        </div>
		
		        <div class="leadership_team_card">
		            <div class="leadership_team_avatar">
		                <img src="${pageContext.request.contextPath}/profileimage?name=milan" alt="Milan Lama">
		            </div>
		            <div class="leadership_team_name">Milan Lama</div>
		            <span class="leadership_team_role">Technical Head & UI/UX Designer</span>
		            <p class="leadership_team_bio"> Overseeing technical tasks with wireframing support</p>
		        </div>
		    </div>
		    <div class="leadership_team_bottom_row">

		        <div class="leadership_team_card leadership_team_card_centered">
		            <div class="leadership_team_avatar">
		                <img src="${pageContext.request.contextPath}/profileimage?name=luckey" alt="Luckey">
		            </div>
		            <div class="leadership_team_name">Luckey Lawaju</div>
		            <span class="leadership_team_role">Technical Strategist</span>
		            <p class="leadership_team_bio">Technical strategies with every departmental support</p>
		        </div>
		
		        <div class="leadership_team_card leadership_team_card_centered">
		            <div class="leadership_team_avatar">
		                <img src="${pageContext.request.contextPath}/profileimage?name=om" alt="Om">
		            </div>
		            <div class="leadership_team_name">Om Prakash Yadav</div>
		            <span class="leadership_team_role">Support Engineer</span>
		            <p class="leadership_team_bio">Oversees operations and support</p>
		        </div>
			</div>
			<div class="accent_line bottom"></div>
		</section>
		
		<section class="locations_showcase_section">
			<div class="accent_line top"></div>
		    <div class="locations_showcase_header">
		        <span class="about_section_eyebrow">Where we are</span>
		        <h2 class="about_section_title">Our <em>Locations</em></h2>
		    </div>
		
		    <div class="locations_showcase_grid">
		        <div class="location_card">
		            <div class="location_card_background">
		               <img src="${pageContext.request.contextPath}/background?name=hall1" alt="Kathmandu">
		            </div>
		            <div class="location_card_overlay"></div>
		
		            <div class="location_card_content">
		                <div class="location_status location_status_open"> Operational</div>
		                <div class="location_name">Kathmandu</div>
		                <div class="location_address">Labim Mall, 3rd Floor,Lalitpur</div>
		
		                <div class="location_chips">
		                    <span class="location_chip">4 Halls</span>
		                    <span class="location_chip">IMAX Laser</span>
		                    <span class="location_chip">800 Seats</span>
		                    <span class="location_chip">Exclusive Lounge Access</span>
		                </div>
		            </div>
		        </div>
	
		        <div class="location_card">
		            <div class="location_card_background">
		                 <img src="${pageContext.request.contextPath}/background?name=hall2" alt="Pokhara">
		            </div>
		            <div class="location_card_overlay"></div>
		            <div class="location_card_content">
		                <div class="location_status location_status_late">Q3 2026</div>
		                <div class="location_name">Pokhara</div>
		                <div class="location_address">Lakeside Mall, Level 4, Pokhara</div>
		                <div class="location_chips">
		                    <span class="location_chip">3 Halls</span>
		                    <span class="location_chip">IMAX 3D</span>
		                    <span class="location_chip">600 Seats</span>
		                    <span class="location_chip">Lakeside View</span>
		                </div>
		            </div>
		        </div>
	
		        <div class="location_card">
		            <div class="location_card_background">
		                 <img src="${pageContext.request.contextPath}/background?name=hall3" alt="Butwal">
		            </div>
		            <div class="location_card_overlay"></div>
		            <div class="location_card_content">
		                <div class="location_status location_status_late">Q4 2026</div>
		                <div class="location_name">Butwal</div>
		                <div class="location_address">Sunrise Mall, 2nd Floor, Deuba Chowk</div>
		                <div class="location_chips">
		                    <span class="location_chip">3 Halls</span>
		                    <span class="location_chip">IMAX Atmos</span>
		                    <span class="location_chip">850 Seats</span>
		                </div>
		            </div>
		        </div>
	
			        <div class="location_card">
		            <div class="location_card_background">
		                <img src="${pageContext.request.contextPath}/background?name=hall4" alt="Biratnagar">
		            </div>
		            <div class="location_card_overlay"></div>
		            <div class="location_card_content">
		                <div class="location_status location_status_late">2030</div>
		                <div class="location_name">Biratnagar</div>
		                <div class="location_address">City Center, 4nd Floor</div>
		                <div class="location_chips">
		                    <span class="location_chip">2 Halls</span>
		                    <span class="location_chip">IMAX Laser 4K</span>
		                    <span class="location_chip">1000+ Seats</span>
		                </div>
		            </div>
		        </div>
		    </div>
		    <div class="accent_line bottom"></div>
		</section>
    </main>
    <jsp:include page="../components/footer.jsp" />

</body>
</html>