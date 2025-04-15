/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */
"<html><head><script src='https://cdn.jsdelivr.net/npm/chart.js'></script></head><body></body></html>";
async function fetchApplcicationsAll() {
    
    try{
        const response = await fetch("api/Applications");
    
    if(!response.ok){
        throw new Error("Failed to fetch applications.");
    }
// Prepare data for Deployment Date chart

        const data = await response.json();
        const appNames = data.map(app => app.appName);
        const deploymentDates = data.map(app => app.deploymentDateFormatted);

        // Prepare data for Critical Rating and Hosting Environment chart
        const criticalRatings = data.map(app => app.criticalRating);
        const hostingEnvironments = data.map(app => app.hostingEnvironment);

          // Prepare data for the chart of UserBase
            const chartLabels = data.map(app => app.appName);
            const chartData = data.map(app => parseInt(app.userBase) || 0);

            // Prepare data for the chart of Authenticatinn Methods
            const authenticationMethod = data.map(app => parseInt(app.authenticationMethod) || 0);
            
            // PolarArea
            const authMethdCounts = data.reduce((counts, app) => {
            counts[app.authenticationMethod] = (counts[app.authenticationMethod] || 0) + 1;
            return counts;
          }, {});
          const authMethdLabels = Object.keys(authMethdCounts);
          const authMethdData = Object.values(authMethdCounts);

            const ctx = document.getElementById('appChart').getContext('2d');
            new Chart(ctx, {
                type: 'polarArea',
                data: {
                    labels:authMethdLabels,
                    datasets: [{
                        label: 'Number of Applications',
                        data: authMethdData,
                        backgroundColor: [
                            'rgba(241, 140, 32, 0.6)',
                            'rgba(54, 162, 235, 0.6)'
                        ],
                        borderColor: [
                            'rgba(241, 140, 32, 1)',
                            'rgba(54, 162, 235, 1)'
                        ],
                            
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
            
            // Prepare data for the chart
            const chartLabelsOne = data.map(app => app.appName);
            const chartDataOne = data.map(app => parseInt(app.userBase) || 0);

            // Render Chart

            const ctx1 = document.getElementById('appChartOne').getContext('2d');
            new Chart(ctx1, {
                type: 'bar',
                data: {
                    labels: chartLabels,
                    datasets: [{
                        label: 'User Base',
                        data: chartData,
                        backgroundColor: [
                                'rgba(255, 99, 132, 0.2)',
                                'rgba(255, 159, 64, 0.2)',
                                'rgba(255, 205, 86, 0.2)',
                                'rgba(75, 192, 192, 0.2)',
                                'rgba(54, 162, 235, 0.2)',
                                'rgba(153, 102, 255, 0.2)',
                                'rgba(201, 203, 207, 0.2)'
                              ],
                    borderColor:  [
                                'rgb(255, 99, 132)',
                                'rgb(255, 159, 64)',
                                'rgb(255, 205, 86)',
                                'rgb(75, 192, 192)',
                                'rgb(54, 162, 235)',
                                'rgb(153, 102, 255)',
                                'rgb(201, 203, 207)'
                                  ],
                        borderWidth: 1,
                        legend: false
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
            
            
    // 1. Prepare data for the Deployment Date Chart:
    // Convert date strings (yyyy-MM-dd) to timestamps (number of milliseconds)
    const appNamesT = data.map(app => app.appName);
    const deploymentTimestamps = data.map(app => new Date(app.deploymentDateFormatted).getTime());

    // Chart 1: Deployment Date Chart (line chart)
    new Chart(document.getElementById('deploymentChart').getContext('2d'), {
      type: 'line',
      data: {
        labels: appNamesT, // X-axis labels (application names)
        datasets: [{
          label: 'Deployment Date',
          data: deploymentTimestamps, // Numeric timestamps for Y values
          backgroundColor: 'rgba(75, 192, 192, 0.2)',
          borderColor: 'rgba(75, 192, 192, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            type: 'linear',
            beginAtZero: false,
            ticks: {
              // Format the numeric timestamps into human-readable dates
              callback: function(value) {
                const date = new Date(value);
                return date.toLocaleDateString();
              }
            }
          }
        }
      }
    });

    // 2. Prepare data for the Critical Rating Chart:
    // Count how many applications are in each critical rating category.
    const ratingCounts = data.reduce((counts, app) => {
      counts[app.criticalRating] = (counts[app.criticalRating] || 0) + 1;
      return counts;
    }, {});
    const ratingLabels = Object.keys(ratingCounts);
    const ratingData = Object.values(ratingCounts);

    // Chart 2: Critical Rating Chart (bar chart)
    new Chart(document.getElementById('ratingChart').getContext('2d'), {
      type: 'bar',
      data: {
        labels: ratingLabels,
        datasets: [{
          label: 'Number of Applications',
          data: ratingData,
          backgroundColor: 'rgba(255, 99, 132, 0.2)',
          borderColor: 'rgba(255, 99, 132, 1)',
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });

    // 3. Prepare data for the Hosting Environment Chart:
    // Count how many applications are in each hosting environment category.
    const environmentCounts = data.reduce((counts, app) => {
      counts[app.hostingEnvironment] = (counts[app.hostingEnvironment] || 0) + 1;
      return counts;
    }, {});
    const environmentLabels = Object.keys(environmentCounts);
    const environmentData = Object.values(environmentCounts);

    // Chart 3: Hosting Environment Chart (bar chart)
    new Chart(document.getElementById('environmentChart').getContext('2d'), {
      type: 'pie',
      data: {
        labels: environmentLabels,
        datasets: [{
          label: 'Number of Applications',
          data: environmentData,
          backgroundColor: [
                            'rgba(255, 99, 132, 0.2)',
                            'rgb(75, 192, 192)',
                            'rgba(253, 209, 7, 0.2)'
                            ],
          borderColor: [
                'rgb(255, 159, 64)',
                'rgb(75, 192, 192)',
                'rgb(253, 209, 7)'
          ],
          borderWidth: 1
        }]
      },
      options: {
        responsive: true,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
    } catch (error) {
        console.error("Error fetching applications:", error);
        alert("Error fetching applications. Please check your server.");
    }
    
    }
fetchApplcicationsAll();
